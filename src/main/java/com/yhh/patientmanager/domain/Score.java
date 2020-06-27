package com.yhh.patientmanager.domain;

/**
 * @Classname score
 * @Description 健康评分实体表
 * @Date 2019/7/3 10:22
 * @Created by WDD
 */
public class Score {
    private Integer id;
    private Integer patientId;
    private Integer examinationId;
    private double score;
    private String cur_condition;
    private String remark;

    public String getCur_condition() {
        return cur_condition;
    }

    public void setCur_condition(String cur_condition) {
        this.cur_condition = cur_condition;
    }

    //关联表
    private String examinationName;
    private String patientName;

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
