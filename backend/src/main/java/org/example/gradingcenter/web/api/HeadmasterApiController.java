package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.users.Headmaster;
import org.example.gradingcenter.service.HeadmasterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/headmasters")
public class HeadmasterApiController {

    private final HeadmasterService headmasterService;

    @GetMapping
    public List<Headmaster> getHeadmasters() {
        return headmasterService.getHeadmasters();
    }

    @GetMapping("/{id}")
    public Headmaster getHeadmaster(@PathVariable long id) {
        return headmasterService.getHeadmaster(id);
    }

    @PostMapping
    public Headmaster createHeadmaster(@RequestBody Headmaster headmaster) {
        return headmasterService.createHeadmaster(headmaster);
    }

    @PutMapping("/{id}")
    public Headmaster updateHeadmaster(@RequestBody Headmaster headmaster, @PathVariable long id) {
        return headmasterService.updateHeadmaster(headmaster, id);
    }

    @DeleteMapping("/{id}")
    public void deleteHeadmaster(@PathVariable long id) {
        headmasterService.deleteHeadmaster(id);
    }

}
