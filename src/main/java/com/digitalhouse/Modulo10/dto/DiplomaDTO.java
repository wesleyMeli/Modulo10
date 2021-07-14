package com.digitalhouse.Modulo10.dto;

import java.math.BigDecimal;
import java.util.List;

public class DiplomaDTO {
    private String message;
    private double avarage;
    private StudentDTO student;

    public DiplomaDTO() {
    }

    public DiplomaDTO(String message, double avarage) {
        this.message = message;
        this.avarage = avarage;
    }

    public DiplomaDTO(StudentDTO student) {
        avarage(student.getMatters());
        this.student = student;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAvarage() {
        return avarage;
    }

    public StudentDTO getStudent() {
        return student;
    }
    private void avarage(List<SubjectDTO> subjectDTO){
        BigDecimal total = subjectDTO.stream().map(SubjectDTO::getNote).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.avarage = total.doubleValue()/subjectDTO.size();
    }
}
