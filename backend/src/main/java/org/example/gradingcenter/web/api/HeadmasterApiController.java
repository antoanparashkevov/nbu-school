package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.service.HeadmasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/headmasters")
public class HeadmasterApiController {

    private final HeadmasterService headmasterService;

    @GetMapping
    public ResponseEntity<?> getHeadmasters() {
        return ResponseEntity.ok().body(headmasterService.getHeadmasters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHeadmaster(@PathVariable long id) {
        return ResponseEntity.ok().body(headmasterService.getHeadmaster(id));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createHeadmaster(@PathVariable long userId) {
        return ResponseEntity.ok().body(headmasterService.createHeadmaster(userId));
    }

    @DeleteMapping("/{id}")
    public void deleteHeadmaster(@PathVariable long id) {
        ResponseEntity.ok().build();
        headmasterService.deleteHeadmaster(id);
    }

}
