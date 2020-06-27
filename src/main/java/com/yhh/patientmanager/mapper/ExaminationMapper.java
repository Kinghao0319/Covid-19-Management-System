package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.Examination;

import java.util.List;
import java.util.Map;

/**
 * @Classname ExaminationMapper
 * @Description None
 * @Date 2019/6/29 20:34
 * @Created by WDD
 */
public interface ExaminationMapper {
    List<Examination> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addExamination(Examination examination);

    int editExamination(Examination examination);

    int deleteExamination(List<Integer> ids);

    int addPatientNum(Integer examinationId);

    void deletePatientNum(Integer examinationId);

    List<Examination> getExaminationById(List<Integer> ids);

    int findByName(String name);
}
