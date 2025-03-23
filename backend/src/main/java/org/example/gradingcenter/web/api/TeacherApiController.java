package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.TeacherInDto;
import org.example.gradingcenter.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherApiController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<?> getTeachers() {
        return ResponseEntity.ok().body(teacherService.getTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable long id) {
        return ResponseEntity.ok().body(teacherService.getTeacher(id));
    }

    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody TeacherInDto teacher, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(teacherService.createTeacher(teacher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }

}
