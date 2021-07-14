package com.digitalhouse.Modulo10.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
public class StudentDTO {

    @NotNull(message = "O nome do estudante não pode ser vazio")
    @Size(min = 8, max = 50, message = "O nome do estudante deve conter ao menos de 8 caracteres e no maximo 50 caracteres")
    @Pattern(regexp = "^[a-z ]+$", message = "O nome do estudante deve conter apenas caracteres numericos em letras minusculas de (a ate z)")
    private String name;

    @Valid
    @NotEmpty(message = "O estudante deve ter ao menos uma disciplina")
    @JsonProperty("subjects")
    private List<SubjectDTO> matters;

    public StudentDTO() {
    }

    public StudentDTO(String name, List<SubjectDTO> subjects) {
        this.name = name;
        this.matters = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectDTO> getMatters() {
        return matters;
    }

    public void setMatters(List<SubjectDTO> subjects) {
        this.matters = subjects;
    }
}
