package org.example.gradingcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.gradingcenter.configuration.ModelMapperConfig;
import org.example.gradingcenter.data.dto.MarkDto;
import org.example.gradingcenter.data.dto.MarkOutDto;
import org.example.gradingcenter.data.entity.Mark;
import org.example.gradingcenter.data.entity.Subject;
import org.example.gradingcenter.data.entity.enums.SubjectName;
import org.example.gradingcenter.data.entity.users.Student;
import org.example.gradingcenter.data.entity.users.Teacher;
import org.example.gradingcenter.data.repository.*;
import org.example.gradingcenter.exceptions.EntityNotFoundException;
import org.example.gradingcenter.service.MarkService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.gradingcenter.util.DataUtil.fetchObjectFromDb;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final SubjectRepository subjectRepository;

    private final GradeRepository gradeRepository;

    private final SchoolRepository schoolRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final ModelMapperConfig mapperConfig;

    @Override
    public List<MarkOutDto> getMarks() {
        return mapperConfig.mapList(markRepository.findAll(), MarkOutDto.class);
    }

    @Override
    public List<MarkOutDto> getMarks(Long studentId) {
        return mapperConfig.mapList(markRepository.findAllByStudent_Id(studentId), MarkOutDto.class);
    }

    @Override
    public MarkOutDto getMark(long id) {
        return mapperConfig.getModelMapper().map(fetchObjectFromDb(markRepository, id, Mark.class), MarkOutDto.class);
    }

    @Override
    public MarkOutDto createMark(MarkDto mark) {
        Mark markToCreate = new Mark();
        setSubject(markToCreate, mark);
        markToCreate.setMark(mark.getMark());
        markToCreate.setStudent(fetchObjectFromDb(studentRepository, mark.getStudentId(), Student.class));
        markToCreate.setTeacher(fetchObjectFromDb(teacherRepository, mark.getTeacherId(), Teacher.class));
        return mapperConfig.getModelMapper().map(markRepository.save(markToCreate), MarkOutDto.class);
    }

    private void setSubject(Mark markToCreate, MarkDto mark) {
        String studentGrade = gradeRepository.findGradeNameByStudentId(mark.getStudentId());
        Long schoolId = schoolRepository.findSchoolIdByStudentId(mark.getStudentId());
        Subject subject = subjectRepository.findByNameAndGrade_NameAndSchool_IdAndTeacher_Id(
                        SubjectName.valueOf(mark.getSubjectName()),
                        studentGrade,
                        schoolId,
                        mark.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("nqma takyv predmet"));
        markToCreate.setSubject(subject);
    }

    @Override
    public void deleteMark(long id) {
        markRepository.deleteById(id);
    }
    
}
