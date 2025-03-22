package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parents")
public class ParentApiController {

    private final ParentService parentService;

    @GetMapping
    public ResponseEntity<?> getParents() {
        return ResponseEntity.ok().body(parentService.getParents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParent(@PathVariable long id) {
        return ResponseEntity.ok().body(parentService.getParent(id));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createParent(@PathVariable long userId) {
        return ResponseEntity.ok().body(parentService.createParent(userId));
    }

    @DeleteMapping("/{id}")
    public void deleteParent(@PathVariable long id) {
        ResponseEntity.ok().build();
        parentService.deleteParent(id);
    }

}
