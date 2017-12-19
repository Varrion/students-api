package com.varion.studentsapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "study_program")
public class StudyProgram {
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "name")
    public String name;
}
