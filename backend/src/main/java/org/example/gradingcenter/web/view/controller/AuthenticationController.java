package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.UserRegisterDto;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.AuthenticationService;
import org.example.gradingcenter.web.view.model.SignupViewModel;
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

    @GetMapping("/signup")
    public String showRegisterPage(Model model) {
        model.addAttribute("signupViewModel", new SignupViewModel());
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

            UserRegisterDto userRegisterDto = new UserRegisterDto();

            userRegisterDto.setFirstName(signupViewModel.getFirstName());
            userRegisterDto.setLastName(signupViewModel.getLastName());
            userRegisterDto.setUsername(signupViewModel.getUsername());
            userRegisterDto.setPassword(signupViewModel.getPassword());
            userRegisterDto.setConfirmPassword(signupViewModel.getConfirmPassword());

            authenticationService.register(userRegisterDto);
            return "redirect:/"; // TODO: Redirect to the login page
        } catch (DuplicateEntityException | EntityNotFoundException ex) {
            String[] exceptionMessage = ex.getMessage().split(" ");
            String fieldName = exceptionMessage[2];
            errors.rejectValue(fieldName, "user_error", ex.getMessage());
            return "sign-up";
        }
    }

}

