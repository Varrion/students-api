package com.varion.studentsapi;

import static org.junit.Assert.assertEquals;

import com.varion.studentsapi.model.Student;
import com.varion.studentsapi.service.StudentService;
import com.varion.studentsapi.service.StudyProgramService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudyProgramService studyProgramService;

	@Before
	public void setUp() {
		studyProgramService.createStudyProgram("test_program");
	}

	@Test
	public void createStudentTest() {
		studentService.createStudent("123456", "Ime", "Prezime", "test_program");

		Student student = studentService.getStudent("123456");
		assertEquals("Ime", student.name);
		assertEquals("Prezime", student.lastName);
		assertEquals("test_program", student.studyProgram.name);
	}

}
