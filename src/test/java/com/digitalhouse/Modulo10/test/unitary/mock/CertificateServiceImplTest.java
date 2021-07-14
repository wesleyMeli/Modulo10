package com.digitalhouse.Modulo10.test.unitary.mock;

import com.digitalhouse.Modulo10.dto.StudentDTO;
import com.digitalhouse.Modulo10.dto.SubjectDTO;
import com.digitalhouse.Modulo10.model.Student;
import com.digitalhouse.Modulo10.repositry.StudentRepository;
import com.digitalhouse.Modulo10.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CertificateServiceImplTest {

    private StudentDTO studentDTO;

    @Autowired
    private StudentService studentService;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void init() {
        List<SubjectDTO> subjectDTO = new ArrayList<>();
        subjectDTO.add(new SubjectDTO("matematica", BigDecimal.valueOf(10)));
        subjectDTO.add(new SubjectDTO("portugues", BigDecimal.valueOf(10)));
        subjectDTO.add(new SubjectDTO("fisica teorica", BigDecimal.valueOf(10)));
        studentDTO = new StudentDTO("angelina marquezine", subjectDTO);
    }

    @Test
    void givenAStudentWithThreeSubjectsGetADiploma(){
        Student student = MAPPER.map(studentDTO, Student.class);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        assertEquals(10.0,studentService.save(studentDTO).getAvarage());
    }

    @Test
    void givenAStudentWithThreeSubjectsAndAverageLessThanFourGetARuntimeExceptionWithMessageTheDisapproval(){
        List<SubjectDTO> subjectDTO = new ArrayList<>();
        subjectDTO.add(new SubjectDTO("matematica", BigDecimal.valueOf(4)));
        subjectDTO.add(new SubjectDTO("portugues", BigDecimal.valueOf(4)));
        subjectDTO.add(new SubjectDTO("fisica teorica", BigDecimal.valueOf(4)));
        studentDTO.setMatters(subjectDTO);
        Student student = MAPPER.map(studentDTO, Student.class);
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Exception ex = assertThrows(RuntimeException.class, () -> {
            studentService.save(studentDTO);
        });

        String expected = "Estudante "+student.getName()+" foi reprovado com a media de 4.0";

        assertEquals(expected,ex.getMessage());
    }

    @Test
    void givenAStudentWithNoSubjectGetARuntimeExceptionWithAReproachMessageAndZeroGrade(){
        studentDTO.setMatters(new ArrayList<>());
        Student student = MAPPER.map(studentDTO, Student.class);
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Exception ex = assertThrows(RuntimeException.class, () -> {
            studentService.save(studentDTO);
        });

        String expected = "Estudante "+student.getName()+" foi reprovado com a media de 0.0";

        assertEquals(expected,ex.getMessage());
    }

}
