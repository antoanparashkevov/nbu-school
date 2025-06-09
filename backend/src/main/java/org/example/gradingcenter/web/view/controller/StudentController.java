package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.StudentOutDto;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.repository.specification.StudentSpecification;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.ParentService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.service.StudentService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final ParentService parentService;

    private final SchoolService schoolService;

    @GetMapping
    public String getStudents(Model model) {
        List<StudentViewModel> students = MapperUtil.mapList(studentService.getStudents(), MapperUtil::dtoToViewModel);
        model.addAttribute("students", students);
        model.addAttribute("searchStudent", new StudentSearchViewModel());
        model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
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

        List<StudentViewModel> students = MapperUtil.mapList(studentService.filterStudents(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("students", students);
        model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "students";
    }

    @GetMapping("/edit-student/{id}")
    public String showEditStudentForm(Model model, @PathVariable Long id) {
        StudentOutDto student = studentService.getStudent(id);
        model.addAttribute("student", MapperUtil.dtoToViewModel(student));
        model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        model.addAttribute("parents", MapperUtil.mapList(parentService.getParents(student.getParentIds()), MapperUtil::dtoToViewModel));
        MarkViewModel markViewModel = new MarkViewModel(4, 10 ,2, 16);
        model.addAttribute("marks", MapperUtil.mapList(parentService.getParents(student.getParentIds()), MapperUtil::dtoToViewModel));
        model.addAttribute("newMark", new MarkViewModel());
        return "student-profile";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentViewModel student,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
            model.addAttribute("parents", MapperUtil.mapList(parentService.getParents(student.getParentIds()), MapperUtil::dtoToViewModel));
            return "student-profile";
        }
        try {
            studentService.updateStudent(MapperUtil.dtoToViewModel(student), id);
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("gradeName", "record_error", ex.getMessage());
            model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
            model.addAttribute("parents", MapperUtil.mapList(parentService.getParents(studentService.getStudent(id).getParentIds()), MapperUtil::dtoToViewModel));
            return "student-profile";
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}
