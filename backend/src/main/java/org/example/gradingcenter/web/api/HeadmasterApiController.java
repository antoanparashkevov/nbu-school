package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.HeadmasterInDto;
import org.example.gradingcenter.service.HeadmasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/headmasters")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
    public ResponseEntity<?> deleteHeadmaster(@PathVariable long id) {
        headmasterService.deleteHeadmaster(id);
        return ResponseEntity.ok().build();
    }

}
