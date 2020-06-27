package com.yhh.patientmanager.domain;

public class Condition {
    private Integer id;
    private String patient_id;
    private String patient_name;
    private String update_date;
    private String this_condition;

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getThis_condition() {
        return this_condition;
    }

    public void setThis_condition(String this_condition) {
        this.this_condition = this_condition;
    }
}
