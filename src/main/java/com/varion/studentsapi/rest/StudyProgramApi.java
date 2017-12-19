package com.varion.studentsapi.rest;

import com.varion.studentsapi.model.StudyProgram;
import com.varion.studentsapi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StudyProgramApi {

    @Autowired
    private StudyProgramService studyProgramService;

    @RequestMapping(value = "/study_programs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudyProgram> getStudyPrograms() {
        return studyProgramService.getAllStudyPrograms();
    } //get

    @ResponseStatus(CREATED)
    @RequestMapping(value = "/study_programs", method = POST)
    public StudyProgram createStudyProgram(@RequestParam String name) {
        return studyProgramService.createStudyProgram(name);
    }

    @RequestMapping(value = "/study_programs/{id}", method = DELETE)
    public void deleteStudyProgram(@PathVariable Long id) {
        studyProgramService.deleteStudyProgram(id);
    }

}
