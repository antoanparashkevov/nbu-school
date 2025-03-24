package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.SubjectDto;
import org.example.gradingcenter.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class SubjectApiController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<?> getSubjects() {
        return ResponseEntity.ok().body(subjectService.getSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubject(@PathVariable long id) {
        return ResponseEntity.ok().body(subjectService.getSubject(id));
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectDto subjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(subjectService.createSubject(subjectDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDto subjectDto, @PathVariable long id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(subjectService.updateSubject(subjectDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }

}
