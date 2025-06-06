package org.example.gradingcenter.web.view.controller;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.repository.specification.StudentSpecification;
import org.example.gradingcenter.service.SchoolService;
import org.example.gradingcenter.service.StudentService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.StudentSearchViewModel;
import org.example.gradingcenter.web.view.model.StudentViewModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final SchoolService schoolService;

    @GetMapping
    public String getStudents(Model model) {
        List<StudentViewModel> students = MapperUtil.dtoToViewModelAsList(studentService.getStudents());
        model.addAttribute("students", students);
        model.addAttribute("searchStudent", new StudentSearchViewModel());
        model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "students";
    }

    @PostMapping("/filter")
    public String getFilteredStudents(Model model, @ModelAttribute("searchStudent") StudentSearchViewModel searchStudent) {
        Specification<Student> spec = StudentSpecification.filterRecords(
                searchStudent.getFirstName(),
                searchStudent.getLastName(),
                searchStudent.getGradeName(),
                searchStudent.getSchoolId(),
                searchStudent.getAbsencesFrom(),
                searchStudent.getAbsencesTo());
        List<StudentViewModel> students = MapperUtil.dtoToViewModelAsList(studentService.filterStudents(spec));
        model.addAttribute("students", students);
        model.addAttribute("schools", MapperUtil.mapList(schoolService.getSchools(), MapperUtil::dtoToViewModel));
        return "students";
    }

//    @GetMapping("/edit-student/{id}")
//    public String showEditstudentForm(Model model, @PathVariable Long id) {
//        model.addAttribute("student", mapperUtil.getModelMapper()
//                .map(studentService.getstudent(id), studentViewModel.class));
//        model.addAttribute("gps", mapperUtil.mapList(doctorService.getGps(), DoctorViewModel.class));
//        return "student-profile";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updatePatient(@PathVariable Long id,
//                                @Valid @ModelAttribute("patient") PatientViewModel patientViewModel,
//                                BindingResult bindingResult,
//                                Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("gps", mapperUtil.mapList(doctorService.getGps(), DoctorViewModel.class));;
//            return "patient-profile";
//        }
//        patientService.updatePatient(mapperUtil.getModelMapper()
//                .map(patientViewModel, PatientDto.class), id);
//        return "redirect:/patients";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deletePatient(@PathVariable Long id) {
//        patientService.deletePatient(id);
//        return "redirect:/patients";
//    }

}
