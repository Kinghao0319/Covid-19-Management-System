package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Examination;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname ExaminationService
 * @Description None
 * @Date 2019/6/29 20:09
 * @Created by WDD
 */
public interface ExaminationService {
    PageBean<Examination> queryPage(Map<String, Object> paramMap);

    int addExamination(Examination examination);

    int editExamination(Examination examination);

    int deleteExamination(List<Integer> ids);

    List<Examination> getExaminationById(List<Integer> ids);

    int findByName(String name);
}
