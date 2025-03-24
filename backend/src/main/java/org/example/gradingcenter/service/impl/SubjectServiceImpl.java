package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.SubjectDto;
import org.example.gradingcenter.data.dto.SubjectOutDto;
import org.example.gradingcenter.data.entity.Grade;
import org.example.gradingcenter.data.entity.School;
import org.example.gradingcenter.data.entity.Subject;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.repository.GradeRepository;
import org.example.gradingcenter.data.repository.SchoolRepository;
import org.example.gradingcenter.data.repository.SubjectRepository;
import org.example.gradingcenter.data.repository.TeacherRepository;
import org.example.gradingcenter.exceptions.DuplicateEntityException;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.gradingcenter.util.DataUtil.fetchObjectFromDb;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final GradeRepository gradeRepository;

    private final SchoolRepository schoolRepository;

    private final TeacherRepository teacherRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<SubjectOutDto> getSubjects() {
        return mapperConfig.mapList(subjectRepository.findAll(), SubjectOutDto.class);
    }

    @Override
    public SubjectOutDto getSubject(long id) {
        return mapperConfig.getModelMapper().map(fetchObjectFromDb(subjectRepository, id, Subject.class), SubjectOutDto.class);
    }

    @Override
    public SubjectOutDto createSubject(SubjectDto subject) {
        if (subjectRepository.existsByNameAndGrade_NameAndSchool_Id(subject.getName(), subject.getGradeName(), subject.getSchoolId())) {
            throw new DuplicateEntityException(Student.class, List.of("name", "grade", "schoolId"),
                    List.of(subject.getName(), subject.getGradeName(), subject.getSchoolId()));
        }
        Subject newSubject = new Subject();
        newSubject.setName(subject.getName());
        setGrade(subject.getGradeName(), subject.getSchoolId(), newSubject);
        newSubject.setSchool(fetchObjectFromDb(schoolRepository, subject.getSchoolId(), School.class));
        setTeacher(subject.getTeacherId(), subject.getSchoolId(), newSubject);
        return mapperConfig.getModelMapper().map(subjectRepository.save(newSubject), SubjectOutDto.class);
    }

    @Override
    public SubjectOutDto updateSubject(SubjectDto subject, long id) {
        return subjectRepository.findById(id)
                .map(subjectToUpdate -> {
                    if (subject.getSchoolId() != null) {
                        subjectToUpdate.setSchool(fetchObjectFromDb(schoolRepository, subject.getSchoolId(), School.class));
                    }
                    setTeacher(subject.getTeacherId(), subjectToUpdate.getSchool().getId(), subjectToUpdate);
                    setGrade(subject.getGradeName(), subjectToUpdate.getSchool().getId(), subjectToUpdate);
                    return mapperConfig.getModelMapper().map(subjectRepository.save(subjectToUpdate), SubjectOutDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException(Subject.class, "id", id));
    }

    @Override
    public void deleteSubject(long id) {
        subjectRepository.deleteById(id);
    }

    private void setTeacher(Long teacherId, Long schoolId, Subject newSubject) {
        if (teacherId == null || schoolId == null || newSubject == null) {
            return;
        }
        teacherRepository.findByIdAndSchool_Id(teacherId, schoolId).ifPresentOrElse(newSubject::setTeacher,
                () -> {
                    throw new EntityNotFoundException(Teacher.class, List.of("id", "schoolId"), List.of(teacherId, schoolId));
                }
        );

    }

    private void setGrade(String gradeName, Long schoolId, Subject newSubject) {
        if (gradeName == null || schoolId == null || newSubject == null) {
            return;
        }
        gradeRepository.findByNameAndSchool_Id(gradeName, schoolId).ifPresentOrElse(newSubject::setGrade,
                () -> {
                    throw new EntityNotFoundException(Grade.class, List.of("name", "schoolId"), List.of(gradeName, schoolId));
                }
        );

    }

}
