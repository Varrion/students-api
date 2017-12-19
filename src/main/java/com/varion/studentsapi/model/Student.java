package com.varion.studentsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
    @Id
    public String index;

    @Column(name = "name")
    public String name;

    @Column(name = "last_name")
    public String lastName;

    @ManyToOne
    @JoinColumn(name = "study_program_id")
    @JsonIgnore
    public StudyProgram studyProgram;
}