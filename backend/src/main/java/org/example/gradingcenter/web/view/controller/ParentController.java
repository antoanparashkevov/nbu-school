package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.enums.Roles;
import org.example.gradingcenter.data.entity.users.Parent;
import org.example.gradingcenter.data.repository.specification.ParentSpecification;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.AuthorizationService;
import org.example.gradingcenter.service.ParentService;
import org.example.gradingcenter.service.StudentService;
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
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;
    private final StudentService studentService;
    private final AuthorizationService authService;

    @GetMapping
    public String getParents(Model model) {
        if (!authService.hasAnyRole(Roles.ROLE_HEADMASTER, Roles.ROLE_ADMIN)){
            return "redirect:/parents/edit-parent/" + authService.getLoggedInUser().getId();
        }
        List<ParentViewModel> parents = mapList(parentService.getParents(), MapperUtil::dtoToViewModel);
        model.addAttribute("parents", parents);
        model.addAttribute("searchParent", new ParentSearchViewModel());
        return "parents";
    }

    @PostMapping("/filter")
    public String getFilteredTeachers(Model model, @ModelAttribute("searchParent") ParentSearchViewModel searchParent) {
        Specification<Parent> spec = ParentSpecification.filterParents(
                searchParent.getFirstName(),
                searchParent.getLastName());
        List<ParentViewModel> parents = mapList(parentService.filterParents(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("parents", parents);
        return "parents";
    }

    @GetMapping("/edit-parent/{id}")
    public String showEditParentForm(Model model, @PathVariable Long id) {
        model.addAttribute("parent", dtoToViewModel(parentService.getParent(id)));
        model.addAttribute("children", mapList(studentService.getStudentsByParentId(id), MapperUtil::dtoToViewModel));
        model.addAttribute("newChild", new StudentSearchViewModel());
        return "parent-profile";
    }

    @PostMapping("/update/{id}")
    public String updateParent(@PathVariable Long id,
                                @Valid @ModelAttribute("parent") ParentViewModel parent,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("children", studentService.getStudentsByParentId(id));
            model.addAttribute("newChild", new StudentSearchViewModel());
            return "parent-profile";
        }
        parentService.updateParent(viewModelToDto(parent), id);
        return "redirect:/parents/edit-parent/" + id;
    }

    @PostMapping("/{parentId}/children/add")
    public String addChild(@PathVariable Long parentId,
                             @ModelAttribute("newChild") @Valid StudentSearchViewModel newChild,
                             BindingResult bindingResult,
                             Model model) {
        try {
            parentService.addChildToParent(parentId, newChild.getEgn());
        } catch (EntityNotFoundException ex) {
            bindingResult.rejectValue("egn", "record_error", ex.getMessage());
            model.addAttribute("parent", dtoToViewModel(parentService.getParent(parentId)));
            model.addAttribute("children", studentService.getStudentsByParentId(parentId));
            return "parent-profile";
        }
        return "redirect:/parents/edit-parent/" + parentId;
    }

    @GetMapping("/{parentId}/children/remove/{childId}")
    public String removeChild(@PathVariable Long parentId, @PathVariable Long childId) {
        parentService.removeChildFromParent(parentId, childId);
        return "redirect:/parents/edit-parent/" + parentId;
    }

}
