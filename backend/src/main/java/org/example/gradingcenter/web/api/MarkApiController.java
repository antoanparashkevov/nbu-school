package org.example.gradingcenter.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.MarkDto;
import org.example.gradingcenter.service.MarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marks")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MarkApiController {

    private final MarkService markService;

    @GetMapping
    public ResponseEntity<?> getMarks() {
        return ResponseEntity.ok().body(markService.getMarks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMark(@PathVariable long id) {
        return ResponseEntity.ok().body(markService.getMark(id));
    }

    @PostMapping
    public ResponseEntity<?> createMark(@Valid @RequestBody MarkDto markDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(markService.createMark(markDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMark(@PathVariable long id) {
        markService.deleteMark(id);
        return ResponseEntity.ok().build();
    }

}
