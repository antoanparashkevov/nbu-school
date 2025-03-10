package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.GradeCreateDto;
import org.example.gradingcenter.data.dto.GradeUpdateDto;
import org.example.gradingcenter.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
public class GradeApiController {

    private final GradeService gradeService;

    @GetMapping
    public ResponseEntity<?> getGrades() {
        return ResponseEntity.ok().body(gradeService.getGrades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGrade(@PathVariable long id) {
        return ResponseEntity.ok().body(this.gradeService.getGrade(id));
    }

    @PostMapping
    public ResponseEntity<?> createGrade(@Valid @RequestBody GradeCreateDto grade, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(gradeService.createGrade(grade));
    }

    @PutMapping
    public ResponseEntity<?> updateGrade(@RequestBody GradeUpdateDto grade, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(gradeService.updateGrade(grade));
    }

    @DeleteMapping("/{id}")
    public void deleteGrade(@PathVariable long id) {
        gradeService.deleteGrade(id);
    }

}
