package com.varion.studentsapi.rest;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.varion.studentsapi.model.Student;
import com.varion.studentsapi.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentApi {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping(value = "/students/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudent(@PathVariable String index) {
        return studentService.getStudent(index);
    }

    @RequestMapping(value = "/students/by_study_program/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudentsByStudyProgram(@PathVariable Long id) {
        return studentService.getStudentsByStudyProgram(id);
    }

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/students", method = POST)
    public Student createStudent(@RequestParam String index, @RequestParam String name,
            @RequestParam String lastName, @RequestParam String studyProgramName) {
        return studentService.createStudent(index, name, lastName, studyProgramName);
    }

    @RequestMapping(value = "/students/{index}", method = PATCH)
    public Student updateStudent(@PathVariable String index,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String studyProgramName) {
        return studentService.updateStudent(index, name, lastName, studyProgramName);
    }

    @RequestMapping(value = "/students/{index}", method = DELETE)
    public void deleteStudent(@PathVariable String index) {
        studentService.deleteStudent(index);
    }

}
