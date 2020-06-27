package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.SelectedExamination;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname SelectedExaminationService
 * @Description None
 * @Date 2019/6/30 10:48
 * @Created by WDD
 */
public interface SelectedExaminationService {
    PageBean<SelectedExamination> queryPage(Map<String, Object> paramMap);

    int addSelectedExamination(SelectedExamination selectedExamination);

    int deleteSelectedExamination(Integer id);

    boolean isPatientId(int id);

    List<SelectedExamination> getAllBySid(int patientid);
}
