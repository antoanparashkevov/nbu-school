package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    public ResponseEntity<?> createSchool(@Valid @RequestBody SchoolDto schoolDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(schoolService.createSchool(schoolDto));
    }

    @PutMapping("/{id}")
    public School updateSchool(@RequestBody School school, @PathVariable long id) {
        return schoolService.updateSchool(school, id);
    }

    @DeleteMapping("/{id}")
    public void deleteSchool(@PathVariable long id) {
        schoolService.deleteSchool(id);
    }

}
