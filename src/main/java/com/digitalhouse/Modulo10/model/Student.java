package com.digitalhouse.Modulo10.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn( name = "STUDENT_ID", referencedColumnName = "ID")
    private List<Matter> matters;

    public Student() {
    }

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Student(Long id, String name, List<Matter> matters) {
        this.id = id;
        this.name = name;
        this.matters = matters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Matter> getMatters() {
        return matters;
    }

    public void setMatters(List<Matter> matters) {
        this.matters = matters;
    }

}
