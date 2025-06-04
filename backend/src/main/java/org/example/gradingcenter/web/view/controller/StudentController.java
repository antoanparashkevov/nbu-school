package org.example.gradingcenter.web.view.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.service.StudentService;
import org.example.gradingcenter.util.MapperUtil;
import org.example.gradingcenter.web.view.model.StudentSearchViewModel;
import org.example.gradingcenter.web.view.model.StudentViewModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public String getStudents(Model model) {
        List<StudentViewModel> students = studentService.getStudents().stream().map(MapperUtil::dtoToViewModel).collect(Collectors.toList());
        model.addAttribute("students", students);
        model.addAttribute("searchStudents", new StudentSearchViewModel());
        //model.addAttribute("gps", mapperUtil.mapList(doctorService.getGps(), DoctorViewModel.class));
        return "students";
    }

//    @PostMapping("/filter")
//    public String getFilteredPatients(Model model, @ModelAttribute("searchPatient") PatientSearchModel searchPatient) {
//        Specification<Patient> spec = PatientSpecification.filterRecords(
//                searchPatient.getFirstName(),
//                searchPatient.getLastName(),
//                searchPatient.getEgn(),
//                searchPatient.getGpId(),
//                localDateToDate(searchPatient.getLastPaidMedicalFrom()),
//                localDateToDate(searchPatient.getLastPaidMedicalTo()));
//        List<PatientViewModel> patients = mapperUtil.mapList(patientService.filterPatients(spec), PatientViewModel.class);
//        model.addAttribute("patients", patients);
//        model.addAttribute("gps", mapperUtil.mapList(doctorService.getGps(), DoctorViewModel.class));
//        return "patients";
//    }
//
//    @GetMapping("/edit-patient/{id}")
//    public String showEditPatientForm(Model model, @PathVariable Long id) {
//        model.addAttribute("patient", mapperUtil.getModelMapper()
//                .map(patientService.getPatient(id), PatientViewModel.class));
//        model.addAttribute("gps", mapperUtil.mapList(doctorService.getGps(), DoctorViewModel.class));
//        return "patient-profile";
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
