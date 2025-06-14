package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.MarkDto;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.repository.specification.StudentSpecification;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.MarkService;
import org.example.gradingcenter.service.ParentService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.service.StudentService;
import org.example.gradingcenter.service.SubjectService;
import org.example.gradingcenter.service.TeacherService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.MarkViewModel;
import org.example.gradingcenter.web.view.model.ParentViewModel;
import org.example.gradingcenter.web.view.model.StudentSearchViewModel;
import org.example.gradingcenter.web.view.model.StudentViewModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping
    public String getStudents(Model model) {
        List<StudentViewModel> students = mapList(studentService.getStudents(), MapperUtil::dtoToViewModel);
        model.addAttribute("students", students);
        model.addAttribute("searchStudent", new StudentSearchViewModel());
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "students";
    }

    @PostMapping("/filter")
    public String getFilteredStudents(Model model, @ModelAttribute("searchStudent") StudentSearchViewModel searchStudent) {
        Specification<Student> spec = StudentSpecification.filterRecords(
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
            return "student-profile";
        }
        try {
            studentService.updateStudent(dtoToViewModel(student), id);
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("gradeName", "record_error", ex.getMessage());
            addNeededAttributesForEditStudentProfileView(model, student);
            return "student-profile";
        }
        return "redirect:/students";
    }

    @PostMapping("/marks/add")
    public String addMark(@ModelAttribute("newMark") @Valid MarkViewModel markViewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "redirect:/students/profile/" + markViewModel.getStudentId();
        markService.createMark(viewModelToDto(markViewModel));
        return "redirect:/students/profile/" + markViewModel.getStudentId();
    }

    @GetMapping("/{studentId}/marks/delete/{markId}")
    public String deleteMark(@PathVariable Long studentId, @PathVariable Long markId) {
        markService.deleteMark(markId);
        return "redirect:/students/profile/" + studentId;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    private void addNeededAttributesForEditStudentProfileView(Model model, StudentViewModel student) {
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        model.addAttribute("parents", mapList(parentService.getParents(student.getParentIds()), MapperUtil::dtoToViewModel));
        model.addAttribute("subjects", mapList(subjectService.getSubjects(student.getGradeName(), student.getSchoolId()), MapperUtil::dtoToViewModel));
        model.addAttribute("teachers", mapList(teacherService.getTeachers(), MapperUtil::dtoToViewModel));
        model.addAttribute("marks", mapList(markService.getMarks(student.getId()), markOutDto -> dtoToViewModel(markOutDto, teacherService.getTeachers())));
    }

}
