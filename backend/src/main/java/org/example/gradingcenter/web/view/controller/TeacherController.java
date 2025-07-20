package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.SubjectDto;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.repository.specification.TeacherSpecification;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.service.AuthorizationService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.service.SubjectService;
import org.example.gradingcenter.service.TeacherService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.gradingcenter.util.MapperUtil.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final SchoolService schoolService;
    private final SubjectService subjectService;
    private final AuthorizationService authService;

    @GetMapping
    public String getTeachers(Model model) {
        if (!authService.hasAnyRole(Roles.ROLE_HEADMASTER, Roles.ROLE_ADMIN)){
            return "redirect:/teachers/edit-teacher/" + authService.getLoggedInUser().getId();
        }
        List<EmployeeViewModel> teachers = mapList(teacherService.getTeachers(), MapperUtil::dtoToViewModel);
        model.addAttribute("teachers", teachers);
        model.addAttribute("searchTeacher", new EmployeeSearchViewModel());
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "teachers";
    }

    @PostMapping("/filter")
    public String getFilteredTeachers(Model model, @ModelAttribute("searchTeacher") EmployeeSearchViewModel searchTeacher) {
        Specification<Teacher> spec = TeacherSpecification.filterTeachers(
                searchTeacher.getFirstName(),
                searchTeacher.getLastName(),
                searchTeacher.getSchoolId());
        List<EmployeeViewModel> teachers = mapList(teacherService.filterTeachers(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("teachers", teachers);
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "teachers";
    }

    @GetMapping("/edit-teacher/{id}")
    public String showEditTeacherForm(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", dtoToViewModel(teacherService.getTeacher(id)));
        model.addAttribute("newSubject", new SubjectViewModel());
        addNeededAttributesForEditTeacherProfileView(model, id);
        return "teacher-profile";
    }

    @PostMapping("/update/{id}")
    public String updateTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("teacher") EmployeeViewModel teacher,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            addNeededAttributesForEditTeacherProfileView(model, id);
            model.addAttribute("newSubject", new SubjectViewModel());
            return "teacher-profile";
        }
        teacherService.updateTeacher(viewModelToDto(teacher), id);
        return "redirect:/teachers/edit-teacher/" + id;
    }

    @PostMapping("/{teacherId}/subjects/add")
    public String addSubject(@PathVariable Long teacherId,
                          @ModelAttribute("newSubject") @Valid SubjectViewModel subjectViewModel,
                          BindingResult bindingResult,
                          Model model) {
        EmployeeViewModel employeeViewModel = dtoToViewModel(teacherService.getTeacher(teacherId));
        if (bindingResult.hasErrors()) {
            model.addAttribute("teacher", employeeViewModel);
            addNeededAttributesForEditTeacherProfileView(model, teacherId);
            return "teacher-profile";
        }
        SubjectDto subjectDto = viewModelToDto(subjectViewModel);
        subjectDto.setTeacherId(teacherId);
        subjectDto.setSchoolId(employeeViewModel.getSchoolId());
        try {
            subjectService.createSubject(subjectDto);
        } catch (DuplicateEntityException ex) {
            bindingResult.rejectValue("name", "record_error", ex.getMessage());
            model.addAttribute("teacher", employeeViewModel);
            addNeededAttributesForEditTeacherProfileView(model, teacherId);
            return "teacher-profile";
        }
        return "redirect:/teachers/edit-teacher/" + teacherId;
    }

    @GetMapping("/{teacherId}/subjects/delete/{subjectId}")
    public String deleteSubject(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return "redirect:/teachers/edit-teacher/" + teacherId;
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }

    private void addNeededAttributesForEditTeacherProfileView(Model model, Long teacherId) {
        model.addAttribute("subjects", mapList(subjectService.getSubjectsByTeacher(teacherId), MapperUtil::dtoToViewModel));
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        model.addAttribute("subjectNames", SubjectName.values());
    }

}
