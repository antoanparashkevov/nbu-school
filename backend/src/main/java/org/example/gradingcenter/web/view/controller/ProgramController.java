package org.example.gradingcenter.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.ProgramSlot;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.repository.specification.ProgramSpecification;
import org.example.gradingcenter.data.repository.specification.TeacherSpecification;
import org.example.gradingcenter.service.ProgramService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.gradingcenter.util.MapperUtil.mapList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    private final SchoolService schoolService;

    @GetMapping
    public String getPrograms(Model model) {
        model.addAttribute("searchProgram", new ProgramSearchViewModel());
        model.addAttribute("programForWeek", new HashMap<>());
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "programs";
    }

    @PostMapping("/filter")
    public String getFilteredPrograms(Model model, @ModelAttribute("searchProgram") ProgramSearchViewModel searchProgram) {
        Specification<ProgramSlot> spec = ProgramSpecification.filterProgram(
                searchProgram.getGradeName(),
                searchProgram.getSchoolId());
        Map<String, List<ProgramSlotViewModel>> programForWeek = programService.filterPrograms(spec).entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                                          entry -> mapList(entry.getValue(), MapperUtil::dtoToViewModel)));
        model.addAttribute("programForWeek", programForWeek);
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "programs";
    }

}
