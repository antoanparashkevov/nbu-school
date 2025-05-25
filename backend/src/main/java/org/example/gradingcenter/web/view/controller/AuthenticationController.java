package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.AuthenticationService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.SignupViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final SchoolService schoolService;

    @GetMapping("/signup")
    public String showRegisterPage(Model model) {
        model.addAttribute("signupViewModel", new SignupViewModel());
        model.addAttribute("roles", Roles.valuesWithoutAdmin());
        model.addAttribute("schools", schoolService.getSchools());
        return "sign-up";
    }

    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute("signupViewModel") SignupViewModel signupViewModel,
                           BindingResult errors) {
        try {
            if (errors.hasErrors()) {
                return "sign-up";
            }
            if (!signupViewModel.getPassword().equals(signupViewModel.getConfirmPassword())) {
                errors.rejectValue("confirmPassword", "password_error",
                        "Password confirmation should match password.");
                return "sign-up";
            }

            UserRegisterDto userRegisterDto = MapperUtil.viewModelToDto(signupViewModel);
            authenticationService.register(userRegisterDto);
            return "redirect:/";
        } catch (DuplicateEntityException | EntityNotFoundException ex) {
            String[] exceptionMessage = ex.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            errors.rejectValue(fieldName, "user_error", ex.getMessage());
            return "sign-up";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Refers to templates/login.html
    }

}

