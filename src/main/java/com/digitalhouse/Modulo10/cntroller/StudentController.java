package com.digitalhouse.Modulo10.cntroller;

import com.digitalhouse.Modulo10.dto.DiplomaDTO;
import com.digitalhouse.Modulo10.dto.StudentDTO;
import com.digitalhouse.Modulo10.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping ("/analyzeNotes")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DiplomaDTO getById( @Valid @RequestBody StudentDTO student){
        return studentService.save(student);
    }
}
