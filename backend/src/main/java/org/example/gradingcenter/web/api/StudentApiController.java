package org.example.gradingcenter.web.api;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.dto.users.StudentInDto;
import org.example.gradingcenter.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.example.gradingcenter.util.DataUtil.getDefaultMessages;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class StudentApiController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok().body(studentService.getStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable long id) {
        return ResponseEntity.ok().body(studentService.getStudent(id));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentInDto student, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody StudentInDto student, BindingResult result, @PathVariable long id) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(getDefaultMessages(result));
        }
        return ResponseEntity.ok().body(studentService.updateStudent(student, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    
}
