package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.SelectedExamination;

import java.util.List;
import java.util.Map;

/**
 * @Classname SelectedExaminationMapper
 * @Description None
 * @Date 2019/6/30 10:56
 * @Created by WDD
 */
public interface SelectedExaminationMapper {
    List<SelectedExamination> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addSelectedExamination(SelectedExamination selectedExamination);

    SelectedExamination findBySelectedExamination(SelectedExamination selectedExamination);

    SelectedExamination findById(Integer id);

    int deleteSelectedExamination(Integer id);

    List<SelectedExamination> isPatientId(int id);

    List<SelectedExamination> getAllBySid(int patientid);
}
