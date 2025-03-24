package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.GradeCreateDto;
import org.example.gradingcenter.data.dto.GradeDto;
import org.example.gradingcenter.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GradeApiController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseEntity<?> getGrades() {
        return ResponseEntity.ok().body(gradeService.getGrades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGrade(@PathVariable long id) {
        return ResponseEntity.ok().body(gradeService.getGrade(id));
    }

    @PostMapping
    public ResponseEntity<?> createGrade(@Valid @RequestBody GradeCreateDto grade, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(gradeService.createGrade(grade));
    }

    @PutMapping
    public ResponseEntity<?> updateGrade(@RequestBody GradeDto grade, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(gradeService.updateGrade(grade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok().build();
    }

}
