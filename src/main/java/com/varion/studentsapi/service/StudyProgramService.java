package com.varion.studentsapi.service;

import com.varion.studentsapi.model.StudyProgram;
import com.varion.studentsapi.repository.StudyProgramRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyProgramService {

    @Autowired
    private StudyProgramRepository studyProgramRepository;

    public List<StudyProgram> getAllStudyPrograms() {
        return studyProgramRepository.findAll();
    }

    public StudyProgram findByName(String name) {
        return studyProgramRepository.findByName(name);
    }

    public StudyProgram createStudyProgram(String name) {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.name = name;
        return studyProgramRepository.saveAndFlush(studyProgram);
    }

    public void deleteStudyProgram(Long id) {
        studyProgramRepository.delete(id);
    }

}
