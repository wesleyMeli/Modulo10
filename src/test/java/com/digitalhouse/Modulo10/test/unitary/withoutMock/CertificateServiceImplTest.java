package com.digitalhouse.Modulo10.test.unitary.withoutMock;

import com.digitalhouse.Modulo10.dto.StudentDTO;
import com.digitalhouse.Modulo10.dto.SubjectDTO;
import com.digitalhouse.Modulo10.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class CertificateServiceImplTest {

    @Autowired
    private StudentService studentService;

    private StudentDTO studentDTO;

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
        assertEquals(10.0,studentService.save(studentDTO).getAvarage());
    }

    @Test
    void givenAStudentWithThreeSubjectsAndAverageLessThanFourGetARuntimeExceptionWithMessageTheDisapproval(){
        List<SubjectDTO> subjectDTO = new ArrayList<>();
        subjectDTO.add(new SubjectDTO("matematica", BigDecimal.valueOf(4)));
        subjectDTO.add(new SubjectDTO("portugues", BigDecimal.valueOf(4)));
        subjectDTO.add(new SubjectDTO("fisica teorica", BigDecimal.valueOf(4)));
        studentDTO.setMatters(subjectDTO);

        Exception ex = assertThrows(RuntimeException.class, () -> {
            studentService.save(studentDTO);
        });

        String expected = "Estudante "+studentDTO.getName()+" foi reprovado com a media de 4.0";

        assertEquals(expected,ex.getMessage());
    }

    @Test
    void givenAStudentWithNoSubjectGetARuntimeExceptionWithAReproachMessageAndZeroGrade(){
        studentDTO.setMatters(new ArrayList<>());

        Exception ex = assertThrows(RuntimeException.class, () -> {
            studentService.save(studentDTO);
        });

        String expected = "Estudante "+studentDTO.getName()+" foi reprovado com a media de 0.0";

        assertEquals(expected,ex.getMessage());
    }
}
