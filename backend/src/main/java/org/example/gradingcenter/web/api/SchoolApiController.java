package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.SchoolDto;
import org.example.gradingcenter.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SchoolApiController {

    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<?> getSchools() {
        return ResponseEntity.ok().body(schoolService.getSchools());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSchool(@PathVariable long id) {
        return ResponseEntity.ok().body(schoolService.getSchool(id));
    }

    @PostMapping
    public ResponseEntity<?> createSchool(@Valid @RequestBody SchoolDto schoolDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(schoolService.createSchool(schoolDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchool(@RequestBody SchoolDto schoolDto, @PathVariable long id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(schoolService.updateSchool(schoolDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchool(@PathVariable long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok().build();
    }

}
