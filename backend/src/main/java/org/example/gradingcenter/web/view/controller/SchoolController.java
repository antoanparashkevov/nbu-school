package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.SchoolOutDto;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.repository.specification.SchoolSpecification;
import org.example.gradingcenter.data.repository.specification.TeacherSpecification;
import org.example.gradingcenter.exceptions.InvalidBusinessDataException;
import org.example.gradingcenter.service.AuthorizationService;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.service.TeacherService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.SchoolSearchViewModel;
import org.example.gradingcenter.web.view.model.SchoolViewModel;
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
@RequestMapping("/schools")
public class SchoolController {

    private final HeadmasterService headmasterService;
    private final SchoolService schoolService;
    private final TeacherService teacherService;
    private final AuthorizationService authService;

    @GetMapping
    public String getSchools(Model model) {
        if (authService.hasAnyRole(Roles.ROLE_HEADMASTER)){
            SchoolOutDto school = schoolService.getSchoolByHeadmaster(authService.getLoggedInUser().getId());
            return "redirect:/schools/edit-school/" + school.getId();
        }
        List<SchoolViewModel> schools = mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel);
        model.addAttribute("schools", schools);
        model.addAttribute("searchSchool", new SchoolSearchViewModel());
        model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
        return "schools";
    }

    @PostMapping("/filter")
    public String getFilteredSchools(Model model, @ModelAttribute("searchSchool") SchoolSearchViewModel searchSchool) {
        Specification<School> spec = SchoolSpecification.filterSchools(
                searchSchool.getName(),
                searchSchool.getAddress(),
                searchSchool.getHeadmasterId());
        List<SchoolViewModel> schools = mapList(schoolService.filterSchools(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("schools", schools);
        model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
        return "schools";
    }

    @GetMapping("/create-school")
    public String showCreateSchoolForm(Model model) {
        model.addAttribute("school", new SchoolViewModel());
        model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
        return "school-create";
    }

    @PostMapping("/create")
    public String createSchool(@Valid @ModelAttribute("school") SchoolViewModel school,
        BindingResult bindingResult,
        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
            return "school-create";
        }
        try {
            SchoolOutDto schoolOutDto = schoolService.createSchool(viewModelToDto(school));
            return "redirect:/schools/edit-school/" + schoolOutDto.getId();
        } catch (InvalidBusinessDataException ex) {
            bindingResult.rejectValue("headmasterId", "headmaster_error", ex.getMessage());
            model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
            return "school-create";
        }
    }

    @GetMapping("/edit-school/{id}")
    public String showEditSchoolForm(Model model, @PathVariable Long id) {
        model.addAttribute("school", dtoToViewModel(schoolService.getSchool(id)));
        model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
        model.addAttribute("teachers", mapList(teacherService.filterTeachers(TeacherSpecification.filterTeachers(null, null, id)),
                                               MapperUtil::dtoToViewModel));
        return "school-edit";
    }

    @PostMapping("/update/{id}")
    public String updateSchool(@PathVariable Long id,
        @Valid @ModelAttribute("school") SchoolViewModel school,
        BindingResult bindingResult,
        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
            model.addAttribute("teachers", mapList(teacherService.filterTeachers(TeacherSpecification.filterTeachers(null, null, id)),
                                                   MapperUtil::dtoToViewModel));
            return "school-edit";
        }
        try {
            schoolService.updateSchool(viewModelToDto(school), id);
        } catch (InvalidBusinessDataException ex) {
            bindingResult.rejectValue("headmasterId", "headmaster_error", ex.getMessage());
            model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
            model.addAttribute("teachers", mapList(teacherService.filterTeachers(TeacherSpecification.filterTeachers(null, null, id)),
                                                   MapperUtil::dtoToViewModel));
            return "school-edit";
        }
        return "redirect:/schools/edit-school/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return "redirect:/schools";
    }

}
