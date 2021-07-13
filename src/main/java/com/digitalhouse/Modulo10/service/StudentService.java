package com.digitalhouse.Modulo10.service;

import com.digitalhouse.Modulo10.dto.DiplomaDTO;
import com.digitalhouse.Modulo10.dto.StudentDTO;
import com.digitalhouse.Modulo10.model.Student;
import com.digitalhouse.Modulo10.repositry.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;

@Service
public class StudentService {

    private static final ModelMapper MAPPER = new ModelMapper();
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private StudentRepository studentRepository;

    private DiplomaDTO generateDiloma(Student student){
        DiplomaDTO diplomaDTO = new DiplomaDTO( MAPPER.map(student, StudentDTO.class));
        diplomaDTO.setMessage("Sua nota média foi de "+diplomaDTO.getAvarage());
        return diplomaDTO;
    }

    public DiplomaDTO save(StudentDTO student){
        return generateDiloma(studentRepository.save(MAPPER.map(student, Student.class)));
    }


}