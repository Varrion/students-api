package com.varion.studentsapi;

import com.jayway.restassured.RestAssured;
import com.varion.studentsapi.model.Student;
import com.varion.studentsapi.model.StudyProgram;
import com.varion.studentsapi.repository.StudentRepository;
import com.varion.studentsapi.repository.StudyProgramRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentApiIntegratonTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudyProgramRepository studyProgramRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Before
    public void setupPort() {
        RestAssured.port = port;
        studentRepository.deleteAll();
        studyProgramRepository.deleteAll();
    }

    @Test
    public void testGetStudents() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgram = studyProgramRepository.saveAndFlush(studyProgram);

        Student student1 = new Student();
        student1.name = "Nena";
        student1.lastName = "Petrovska";
        student1.index = "153027";
        student1.studyProgram = studyProgram;
        studentRepository.saveAndFlush(student1);

        when().
                get("/students").
        then().
                statusCode(200).
                body("name[0]", CoreMatchers.is("Nena")).
                body("lastName[0]", CoreMatchers.is("Petrovska")).
                body("index[0]", CoreMatchers.is("153027")).
                body("studyProgram[0]", CoreMatchers.nullValue());
    }

    @Test
    public void testGetStudentByIndex() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgram = studyProgramRepository.saveAndFlush(studyProgram);

        Student student1 = new Student();
        student1.name = "Nena";
        student1.lastName = "Petrovska";
        student1.index = "153027";
        student1.studyProgram = studyProgram;
        studentRepository.saveAndFlush(student1);

        when().
                get("/students/153027").
        then().
                statusCode(200).
                body("name", CoreMatchers.is("Nena")).
                body("lastName", CoreMatchers.is("Petrovska")).
                body("index", CoreMatchers.is("153027")).
                body("studyProgram", CoreMatchers.nullValue());
    }

    @Test
    public void testGetStudentsByStudyPrograms() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgram = studyProgramRepository.saveAndFlush(studyProgram);

        Student student1 = new Student();
        student1.name = "Nena";
        student1.lastName = "Petrovska";
        student1.index = "153027";
        student1.studyProgram = studyProgram;
        studentRepository.saveAndFlush(student1);

        when().
                get("/students/by_study_program/" + studyProgram.id).
        then().
                statusCode(200).
                body("name[0]", CoreMatchers.is("Nena")).
                body("lastName[0]", CoreMatchers.is("Petrovska")).
                body("index[0]", CoreMatchers.is("153027")).
                body("studyProgram[0]", CoreMatchers.nullValue());
    }

    @Test
    public void testPostStudentSuccess() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgramRepository.saveAndFlush(studyProgram);

        given().
                queryParam("index", "153033").
                queryParam("name", "Tomislav").
                queryParam("lastName", "Anastasovski").
                queryParam("studyProgramName", "test_study_program").
        when().
                post("/students").
        then().
                statusCode(201);

        List<Student> allStudents = studentRepository.findAll();
        assertEquals(1, allStudents.size());
        assertEquals("Tomislav", allStudents.get(0).name);
        assertEquals("ANastasovski", allStudents.get(0).lastName);
        assertEquals("153033", allStudents.get(0).index);
        assertEquals("test_study_program", allStudents.get(0).studyProgram.name);
    }

    @Test
    public void testPostStudentMissingName() {
        given().
                queryParam("index", "153027").
                queryParam("lastName", "Petrovska").
                queryParam("studyProgramName", "test_study_program").
        when().
                post("/students").
        then().
                statusCode(400);
    }

    @Test
    public void testPostStudentMissingIndex() {
        given().
                queryParam("name", "Tomislav").
                queryParam("lastName", "Anastasovski").
                queryParam("studyProgramName", "test_study_program").
        when().
                post("/students").
        then().
                statusCode(400);
    }

    @Test
    public void testPostStudentMissingLastName() {
        given().
                queryParam("index", "153033").
                queryParam("name", "Tomislav").
                queryParam("studyProgramName", "test_study_program").
        when().
                post("/students").
        then().
                statusCode(400);
    }

    @Test
    public void testPostStudentMissingStudyProgram() {
        given().
                queryParam("index", "153033").
                queryParam("name", "Tomislav").
                queryParam("lastName", "Anastasovski").
        when().
                post("/students").
        then().
                statusCode(400);
    }

    @Test
    public void testPostStudentNotExistingStudyProgram() {
        given().
                queryParam("index", "153033").
                queryParam("name", "Tomislav").
                queryParam("lastName", "Anastasovski").
                queryParam("studyProgramName", "test_study_program1").
        when().
                post("/students").
        then().
                statusCode(400);
    }

    @Test
    public void testPostStudentWrongIndex() {
        given().
                queryParam("index", "153032").
                queryParam("name", "Tomislav").
                queryParam("lastName", "Anastasovski").
                queryParam("studyProgramName", "test_study_program").
        when().
                post("/students").
        then().
                statusCode(400);
    }

    @Test
    public void testPatchStudentSuccess() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgram = studyProgramRepository.saveAndFlush(studyProgram);

        Student student1 = new Student();
        student1.name = "Tomislav";
        student1.lastName = "Anastasovski";
        student1.index = "153033";
        student1.studyProgram = studyProgram;
        studentRepository.saveAndFlush(student1);

        given().
                queryParam("name", "Tomislav1").
                queryParam("lastName", "Anastasovski1").
        when().
                patch("/students/153033").
        then().
                statusCode(200).
                body("name", CoreMatchers.is("Tomislav1")).
                body("lastName", CoreMatchers.is("Anastasovski1")).
                body("index", CoreMatchers.is("153033")).
                body("studyProgram", CoreMatchers.nullValue());

        List<Student> allStudents = studentRepository.findAll();
        assertEquals(1, allStudents.size());
        assertEquals("Tomislav1", allStudents.get(0).name);
        assertEquals("Anastasovski1", allStudents.get(0).lastName);
        assertEquals("153033", allStudents.get(0).index);
        assertEquals("test_study_program", allStudents.get(0).studyProgram.name);
    }

    @Test
    public void testPatchNonExistingStudent() {
        given().
                queryParam("name", "Tomislav1").
        when().
                patch("/students/153033").
        then().
                statusCode(404);
    }

    @Test
    public void testPatchStudentNonExistingStudyProgram() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgram = studyProgramRepository.saveAndFlush(studyProgram);

        Student student1 = new Student();
        student1.name = "Tomislav";
        student1.lastName = "Anastasovski";
        student1.index = "153033";
        student1.studyProgram = studyProgram;
        studentRepository.saveAndFlush(student1);

        given().
                queryParam("name", "Tomislav1").
                queryParam("lastName", "Anastasovski1").
                queryParam("studyProgramName", "study_program_updated").
        when().
                patch("/students/153033").
        then().
                statusCode(400);
    }

    @Test
    public void testDeleteStudentSuccess() {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = "test_study_program";
        studyProgram = studyProgramRepository.saveAndFlush(studyProgram);

        Student student1 = new Student();
        student1.name = "Tomislav";
        student1.lastName = "Anastasovski";
        student1.index = "153033";
        student1.studyProgram = studyProgram;
        studentRepository.saveAndFlush(student1);

        when().
                delete("/students/153033").
        then().
                statusCode(200);

        assertEquals(0, studentRepository.findAll().size());
    }

}
