package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.web.view.model.SchoolViewModel;
import org.springframework.ui.Model;
import org.example.gradingcenter.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final SchoolService schoolService;
    private final HeadmasterService headmasterService;



    public AdminController(UserService userService, SchoolService schoolService, HeadmasterService headmasterService) {
        this.userService = userService;
        this.schoolService = schoolService;
        this.headmasterService = headmasterService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("schoolViewModel", new SchoolViewModel());
        model.addAttribute("headmasters", headmasterService.getHeadmasters());
        return "admin";
    }

    @GetMapping("/admin/users/{id}/add-role/{role}")
    public String addRoleToUser(@PathVariable Long id, @PathVariable String role) {
        userService.addRoleToUser(id, role);
        return "redirect:/admin";
    }

    @GetMapping("/admin/users/{id}/remove-role/{role}")
    public String removeRoleFromUser(@PathVariable Long id, @PathVariable String role) {
        userService.removeRoleFromUser(id, role);
        return "redirect:/admin";
    }



    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("schoolViewModel", new SchoolViewModel());
        model.addAttribute("headmasters", headmasterService.getHeadmasters());
        return "admin";
    }


    @PostMapping("/newschool")
    public String createSchool(@Valid @ModelAttribute("schoolViewModel") SchoolViewModel schoolViewModel,
                               BindingResult errors,
                               Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("headmasters", headmasterService.getHeadmasters());
            return "admin";
        }

        try {
            SchoolDto schoolDto = new SchoolDto();
            schoolDto.setName(schoolViewModel.getName());
            schoolDto.setAddress(schoolViewModel.getAddress());
            schoolDto.setHeadmasterId(schoolViewModel.getHeadmasterId());

            schoolService.createSchool(schoolDto);

            model.addAttribute("message", "School created successfully!");
            model.addAttribute("schoolViewModel", new SchoolViewModel());
            model.addAttribute("headmasters", headmasterService.getHeadmasters());
            model.addAttribute("users", userService.getUsers());

            return "admin";
        } catch (DuplicateEntityException | EntityNotFoundException ex) {
            errors.rejectValue("name", "school_error", ex.getMessage());
            model.addAttribute("headmasters", headmasterService.getHeadmasters());
            return "admin";
        }
    }


}

