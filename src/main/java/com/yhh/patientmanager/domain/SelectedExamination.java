package com.yhh.patientmanager.domain;

/**
 * @Classname SelectedExamination
 * @Description 预约体检信息实体类
 * @Date 2019/6/30 10:30
 * @Created by WDD
 */
public class SelectedExamination {
    private Integer id;
    private Integer patientId;
    private Integer examinationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
    }
}
