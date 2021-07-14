package com.digitalhouse.Modulo10.teste;

import com.digitalhouse.Modulo10.dto.StudentDTO;
import com.digitalhouse.Modulo10.dto.SubjectDTO;
import com.digitalhouse.Modulo10.repositry.StudentRepository;
import com.digitalhouse.Modulo10.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    private StudentDTO studentDTO;

    @BeforeEach
    void init() {
        studentRepository.deleteAll();
        List<SubjectDTO> subjectDTO = new ArrayList<>();
        subjectDTO.add(new SubjectDTO("matematica", BigDecimal.valueOf(10)));
        subjectDTO.add(new SubjectDTO("portugues", BigDecimal.valueOf(5)));
        subjectDTO.add(new SubjectDTO("fisica teorica", BigDecimal.valueOf(8)));
        studentDTO = new StudentDTO("angelina marquezine", subjectDTO);
    }

    @Test
    void givenAStudentWithThreeSubjectsGetACreatedStatus() throws Exception {
        mock.perform(post("/student/analyzeNotes").
                contentType("application/json").content(mapper.writeValueAsString(studentDTO))).andExpect(status().isCreated());
    }

    @Test
    void givenAStudentWithThreeSubjectsGetABadRequestStatus() throws Exception {
        studentDTO.setName("Angelina Robsberta");
        mock.perform(post("/student/analyzeNotes").
                contentType("application/json").content(mapper.writeValueAsString(studentDTO))).andExpect(status().isBadRequest());
    }

    @Test
    void givenAStudentWithThreeSubjectsGetABadRequestStatusWithADisapprovalMessage() throws Exception {
        studentDTO.setMatters(new ArrayList<>());
        mock.perform(post("/student/analyzeNotes").
                contentType("application/json").content(mapper.writeValueAsString(studentDTO))).
                andExpect(status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.Reprovado").value("Estudante "+studentDTO.getName()+" foi reprovado com a media de 0.0"));
    }

}
