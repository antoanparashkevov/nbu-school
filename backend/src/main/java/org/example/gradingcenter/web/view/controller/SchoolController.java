package org.example.gradingcenter.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.data.repository.specification.HeadmasterSpecification;
import org.example.gradingcenter.data.repository.specification.SchoolSpecification;
import org.example.gradingcenter.service.HeadmasterService;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.EmployeeSearchViewModel;
import org.example.gradingcenter.web.view.model.EmployeeViewModel;
import org.example.gradingcenter.web.view.model.SchoolSearchViewModel;
import org.example.gradingcenter.web.view.model.SchoolViewModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.gradingcenter.util.MapperUtil.dtoToViewModel;
import static org.example.gradingcenter.util.MapperUtil.mapList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schools")
public class SchoolController {

    private final HeadmasterService headmasterService;
    private final SchoolService schoolService;

    @GetMapping
    public String getSchools(Model model) {
        List<SchoolViewModel> schools = mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel);
        model.addAttribute("schools", schools);
        model.addAttribute("searchSchool", new SchoolSearchViewModel());
        model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
        return "schools";
    }

    @PostMapping("/filter")
    public String getFilteredTeachers(Model model, @ModelAttribute("searchSchool") SchoolSearchViewModel searchSchool) {
        Specification<School> spec = SchoolSpecification.filterRecords(
                searchSchool.getName(),
                searchSchool.getAddress(),
                searchSchool.getHeadmasterId());
        List<SchoolViewModel> schools = mapList(schoolService.filterSchools(spec), MapperUtil::dtoToViewModel);
        model.addAttribute("schools", schools);
        model.addAttribute("headmasters", mapList(headmasterService.getHeadmasters(), MapperUtil::dtoToViewModel));
        return "schools";
    }

    @GetMapping("/edit-headmaster/{id}")
    public String showEditTeacherForm(Model model, @PathVariable Long id) {
        model.addAttribute("headmaster", dtoToViewModel(headmasterService.getHeadmaster(id)));
        model.addAttribute("schools", mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "headmaster-profile";
    }

}
