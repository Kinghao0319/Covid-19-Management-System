package com.yhh.patientmanager.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
public class Medicine {
    private Integer id;
    private Integer patient_id;
    private String patient_name;
    private String patient_hometown;
    private String medicine_name;
    private String if_use;

    private Date start_time;

    public String getPatient_hometown() {
        return patient_hometown;
    }

    public void setPatient_hometown(String patient_hometown) {
        this.patient_hometown = patient_hometown;
    }

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

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getIf_use() {
        return if_use;
    }

    public void setIf_use(String if_use) {
        this.if_use = if_use;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }
}
