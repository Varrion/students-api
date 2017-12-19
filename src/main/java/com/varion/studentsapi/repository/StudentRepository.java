package com.varion.studentsapi.repository;

import com.varion.studentsapi.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findByStudyProgramId(Long studyProgramId);

}
