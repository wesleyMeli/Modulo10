package com.digitalhouse.Modulo10.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Validated
@JsonPropertyOrder({"subject", "note"})
public class SubjectDTO {

    @NotNull(message = "O nome da materia não pode ser vazio")
    @Size(min = 8, max = 50, message = "O nome da materia deve conter ao menos 8 caracteres e no maximo 50 caracteres")
    @Pattern(regexp = "^[a-z ]+$", message = "O nome da materia deve conter apenas caracteres numericos em letras minusculas de (a ate z)")
    @JsonProperty("subject")
    private String matterName;

    @NotNull(message = "A nota não pode ser vazio")
    @DecimalMax(value = "10.0",message = "A nota não pode ser maior que 10")
    @DecimalMin(value = "0.0",message = "A nota não pode ser menor que 0")
    private BigDecimal note;

    public SubjectDTO() {
    }

    public SubjectDTO(String matterName, BigDecimal note) {
        this.matterName = matterName;
        this.note = note;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

    public BigDecimal getNote() {
        return note;
    }

    public void setNote(BigDecimal note) {
        this.note = note;
    }
}
