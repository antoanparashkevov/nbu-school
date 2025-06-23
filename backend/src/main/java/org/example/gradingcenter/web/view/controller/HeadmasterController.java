package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.repository.specification.HeadmasterSpecification;
import org.example.gradingcenter.exceptions.InvalidBusinessDataException;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.EmployeeViewModel;
import org.example.gradingcenter.web.view.model.EmployeeSearchViewModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.gradingcenter.util.MapperUtil.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/headmasters")
public class HeadmasterController {

    private final HeadmasterService headmasterService;
    private final SchoolService schoolService;

    @GetMapping
    public String getHeadmasters(Model model) {
        List<EmployeeViewModel> headmasters = mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel);
        model.addAttribute("headmasters", headmasters);
        model.addAttribute("searchHeadmaster", new EmployeeSearchViewModel());
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "headmasters";
    }

    @PostMapping("/filter")
    public String getFilteredTeachers(Model model, @ModelAttribute("searchHeadmaster") EmployeeSearchViewModel searchHeadmaster) {
        Specification<Headmaster> spec = HeadmasterSpecification.filterRecords(
                searchHeadmaster.getFirstName(),
                searchHeadmaster.getLastName(),
                searchHeadmaster.getSchoolId());
        List<EmployeeViewModel> headmasters = mapList(headmasterService.filterHeadmasters(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("headmasters", headmasters);
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "headmasters";
    }

    @GetMapping("/edit-headmaster/{id}")
    public String showEditTeacherForm(Model model, @PathVariable Long id) {
        model.addAttribute("headmaster", dtoToViewModel(headmasterService.getHeadmaster(id)));
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "headmaster-profile";
    }

    @PostMapping("/update/{id}")
    public String updateHeadmaster(@PathVariable Long id,
                                @Valid @ModelAttribute("headmaster") EmployeeViewModel headmaster,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
            return "headmaster-profile";
        }
        try {
            headmasterService.updateHeadmaster(viewModelToDto(headmaster), id);
        } catch (InvalidBusinessDataException ex) {
            bindingResult.rejectValue("schoolId", "headmaster_error", ex.getMessage());
            model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
            return "headmaster-profile";
        }
        return "redirect:/headmasters/edit-headmaster/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteHeadmaster(@PathVariable Long id) {
        headmasterService.deleteHeadmaster(id);
        return "redirect:/headmasters";
    }

}
