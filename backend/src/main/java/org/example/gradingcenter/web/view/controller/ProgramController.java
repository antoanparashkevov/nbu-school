package org.example.gradingcenter.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.service.ProgramService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.ParentSearchViewModel;
import org.example.gradingcenter.web.view.model.ParentViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.example.gradingcenter.util.MapperUtil.mapList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    @GetMapping
    public String getParents(Model model) {
//        List<ParentViewModel> parents = mapList(parentService.getParents(), MapperUtil::dtoToViewModel);
//        model.addAttribute("program", parents);
//        model.addAttribute("searchParent", new ParentSearchViewModel());
        return "programs";
    }

}
