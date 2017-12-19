package com.varion.studentsapi;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

import com.jayway.restassured.RestAssured;
import com.varion.studentsapi.model.StudyProgram;
import com.varion.studentsapi.repository.StudentRepository;
import com.varion.studentsapi.repository.StudyProgramRepository;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudyProgramApiIntegratonTest {

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
    public void testPostStudyPrograms() {
        given().
                queryParam("name", "test_study_program").
        when().
                post("/study_programs").
        then().
                statusCode(201);

        List<StudyProgram> allStudyPrograms = studyProgramRepository.findAll();
        assertEquals(1, allStudyPrograms.size());
        assertEquals("test_study_program", allStudyPrograms.get(0).name);
    }

    @Test
    public void testGetStudyPrograms() {
        StudyProgram program1 = new StudyProgram();
        program1.name = "program_1";
        studyProgramRepository.saveAndFlush(program1);

        StudyProgram program2 = new StudyProgram();
        program2.name = "program_2";
        studyProgramRepository.saveAndFlush(program2);

        when().
                get("/study_programs").
        then().
                statusCode(200).
                body("name[0]", CoreMatchers.is("program_1")).
                body("name[1]", CoreMatchers.is("program_2"));
    }

    @Test
    public void testDeleteStudyPrograms() {
        StudyProgram program1 = new StudyProgram();
        program1.name = "program_1";
        StudyProgram savedProgram1 = studyProgramRepository.saveAndFlush(program1);

        StudyProgram program2 = new StudyProgram();
        program2.name = "program_2";
        studyProgramRepository.saveAndFlush(program2);


        when().
                delete("/study_programs/" + savedProgram1.id).
        then().
                statusCode(200);

        assertEquals(1, studyProgramRepository.findAll().size());
        assertEquals("program_2", studyProgramRepository.findAll().get(0).name);
    }

}
