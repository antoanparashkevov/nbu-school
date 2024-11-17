package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
public class SchoolApiController {

    private final SchoolService schoolService;

    @GetMapping
    public List<School> getSchools() {
        return schoolService.getSchools();
    }

    @GetMapping("/{id}")
    public School getSchool(@PathVariable long id) {
        return schoolService.getSchool(id);
    }

    @PostMapping
    public School createSchool(@RequestBody School school) {
        return schoolService.createSchool(school);
    }

    @PutMapping("/{id}")
    public School createSchool(@RequestBody School school, @PathVariable long id) {
        return schoolService.updateSchool(school, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable long id) {
        schoolService.deleteSchool(id);
    }

}
