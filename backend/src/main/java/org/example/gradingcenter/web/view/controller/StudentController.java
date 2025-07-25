package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.MarkDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.repository.specification.StudentSpecification;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.*;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.MarkViewModel;
import org.example.gradingcenter.web.view.model.StudentSearchViewModel;
import org.example.gradingcenter.web.view.model.StudentViewModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.gradingcenter.util.MapperUtil.dtoToViewModel;
import static org.example.gradingcenter.util.MapperUtil.mapList;
import static org.example.gradingcenter.util.MapperUtil.viewModelToDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ParentService parentService;
    private final SchoolService schoolService;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final MarkService markService;
    private final AuthorizationService authService;

    @GetMapping
    public String getStudents(Model model) {
        if (authService.hasAnyRole(Roles.ROLE_STUDENT)){
            return "redirect:/students/edit-student/" + authService.getLoggedInUser().getId();
        }
        List<StudentViewModel> students = mapList(studentService.getStudents(), MapperUtil::dtoToViewModel);
        model.addAttribute("students", students);
        model.addAttribute("searchStudent", new StudentSearchViewModel());
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "students";
    }

    @PostMapping("/filter")
    public String getFilteredStudents(Model model, @ModelAttribute("searchStudent") StudentSearchViewModel searchStudent) {
        Specification<Student> spec = StudentSpecification.filterStudents(
                searchStudent.getEgn(),
                searchStudent.getFirstName(),
                searchStudent.getLastName(),
                searchStudent.getGradeName(),
                searchStudent.getSchoolId(),
                searchStudent.getAbsencesFrom(),
                searchStudent.getAbsencesTo());
        List<StudentViewModel> students = mapList(studentService.filterStudents(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("students", students);
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "students";
    }

    @GetMapping("/edit-student/{id}")
    public String showEditStudentForm(Model model, @PathVariable Long id) {
        StudentOutDto student = studentService.getStudent(id);
        StudentViewModel studentViewModel = dtoToViewModel(student);
        model.addAttribute("student", studentViewModel);
        addNeededAttributesForEditStudentProfileView(model, studentViewModel);
        model.addAttribute("newMark", new MarkViewModel());
        return "student-profile";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentViewModel student,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            addNeededAttributesForEditStudentProfileView(model, student);
            model.addAttribute("newMark", new MarkViewModel());
            return "student-profile";
        }
        try {
            studentService.updateStudent(MapperUtil.viewModelToDto(student), id);
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("gradeName", "student_error", ex.getMessage());
            addNeededAttributesForEditStudentProfileView(model, student);
            model.addAttribute("newMark", new MarkViewModel());
            return "student-profile";
        }
        return "redirect:/students/edit-student/" + id;
    }

    @PostMapping("/{studentId}/marks/add")
    public String addMark(@PathVariable Long studentId,
                          @ModelAttribute("newMark") @Valid MarkViewModel markViewModel,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()){
            StudentOutDto student = studentService.getStudent(studentId);
            StudentViewModel studentViewModel = dtoToViewModel(student);
            model.addAttribute("student", studentViewModel);
            addNeededAttributesForEditStudentProfileView(model, studentViewModel);
            return "student-profile";
        }
        MarkDto markDto = viewModelToDto(markViewModel);
        markDto.setStudentId(studentId);
        markService.createMark(markDto);
        return "redirect:/students/edit-student/" + studentId;
    }

    @GetMapping("/{studentId}/marks/delete/{markId}")
    public String deleteMark(@PathVariable Long studentId, @PathVariable Long markId) {
        markService.deleteMark(markId);
        return "redirect:/students/edit-student/" + studentId;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    private void addNeededAttributesForEditStudentProfileView(Model model, StudentViewModel student) {
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        model.addAttribute("parents", mapList(parentService.getParents(student.getId()), MapperUtil::dtoToViewModel));
        model.addAttribute("marks", mapList(markService.getMarks(student.getId()), markOutDto -> dtoToViewModel(markOutDto, teacherService.getTeachers())));
        if (authService.hasAnyRole(Roles.ROLE_ADMIN)) {
            model.addAttribute("teachers", mapList(teacherService.getTeachers(), MapperUtil::dtoToViewModel));
            model.addAttribute("subjects", mapList(subjectService.getSubjects(student.getGradeName(), student.getSchoolId()), MapperUtil::dtoToViewModel));
        } else {
            model.addAttribute("subjects", mapList(subjectService.getSubjects(student.getGradeName(),
                            student.getSchoolId(),
                            authService.getLoggedInUser().getId()),
                    MapperUtil::dtoToViewModel));
            model.addAttribute("teachers", List.of(MapperUtil.dtoToViewModel(teacherService.getTeacher(authService.getLoggedInUser().getId()))));

        }
    }

}
