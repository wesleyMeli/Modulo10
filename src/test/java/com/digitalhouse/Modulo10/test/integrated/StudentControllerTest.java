package com.digitalhouse.Modulo10.test.integrated;

import com.digitalhouse.Modulo10.dto.DiplomaDTO;
import com.digitalhouse.Modulo10.dto.StudentDTO;
import com.digitalhouse.Modulo10.dto.SubjectDTO;
import com.digitalhouse.Modulo10.repositry.StudentRepository;
import com.digitalhouse.Modulo10.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void givenAStudentUnnamedWithThreeSubjectsGetABadRequestStatus() throws Exception {
        studentDTO.setName(null);
        mock.perform(post("/student/analyzeNotes").
                contentType("application/json").content(mapper.writeValueAsString(studentDTO))).andExpect(status().isBadRequest());
    }

    @Test
    void givenAStudentWithThreeSubjectsGetABadRequestStatusWithADisapprovalMessage() throws Exception {
        List<SubjectDTO> subjectDTOS = new ArrayList<>();
        subjectDTOS.add(new SubjectDTO("quimica basica", BigDecimal.valueOf(4)));
        subjectDTOS.add(new SubjectDTO("matematica", BigDecimal.valueOf(4)));
        studentDTO.setMatters(subjectDTOS);
        mock.perform(post("/student/analyzeNotes").
                contentType("application/json").content(mapper.writeValueAsString(studentDTO))).
                andExpect(status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.Reprovado").value("Estudante "+studentDTO.getName()+" foi reprovado com a media de "+new DiplomaDTO(studentDTO).getAvarage()));
    }
    @Test
    void givenAStudentWithThreeSubjectsGetABadRequestStatusWithErrorMessageNotSubject() throws Exception {
        studentDTO.setMatters(new ArrayList<>());
        mock.perform(post("/student/analyzeNotes").
                contentType("application/json").content(mapper.writeValueAsString(studentDTO))).
                andExpect(status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("*").value("O estudante deve ter ao menos uma disciplina"));
    }
}
