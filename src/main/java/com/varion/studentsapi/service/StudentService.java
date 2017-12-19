package com.varion.studentsapi.service;

import com.varion.studentsapi.exception.IndexNotValidException;
import com.varion.studentsapi.exception.StudentNotFoundException;
import com.varion.studentsapi.exception.StudyProgramNotExistingException;
import com.varion.studentsapi.model.Student;
import com.varion.studentsapi.model.StudyProgram;
import com.varion.studentsapi.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudyProgramService studyProgramService;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(String index) {
        return studentRepository.findOne(index);
    }

    public List<Student> getStudentsByStudyProgram(Long studyProgramId) {
        return studentRepository.findByStudyProgramId(studyProgramId);
    }

    public Student createStudent(String index, String name, String lastName, String studyProgramName) {
        if (index.length() != 6) {
            throw new IndexNotValidException(index);
        }

        StudyProgram studyProgram = studyProgramService.findByName(studyProgramName);
        if (studyProgram == null) {
            throw new StudyProgramNotExistingException(studyProgramName);
        }

        Student student = new Student();
        student.index = index;
        student.name = name;
        student.lastName = lastName;
        student.studyProgram = studyProgram;

        return studentRepository.saveAndFlush(student);
    }

    public Student updateStudent(String index, String name, String lastName, String studyProgramName) {
        Student student = studentRepository.findOne(index);
        if (student == null) {
            throw new StudentNotFoundException(index);
        }

        if (name != null) {
            student.name = name;
        }

        if (lastName != null) {
            student.lastName = lastName;
        }

        if (studyProgramName != null) {
            StudyProgram studyProgram = studyProgramService.findByName(studyProgramName);
            if (studyProgram == null) {
                throw new StudyProgramNotExistingException(studyProgramName);
            }
            student.studyProgram = studyProgram;
        }

        return studentRepository.saveAndFlush(student);
    }

    public void deleteStudent(String index) {
        Student student = studentRepository.findOne(index);

        if (student == null) {
            throw new StudentNotFoundException(index);
        }

        studentRepository.delete(index);
    }
}
