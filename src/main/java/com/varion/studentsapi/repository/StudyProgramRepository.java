package com.varion.studentsapi.repository;

import com.varion.studentsapi.model.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {

    StudyProgram findByName(String name);
}
