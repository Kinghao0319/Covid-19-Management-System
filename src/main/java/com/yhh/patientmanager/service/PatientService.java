package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Claspnumame PatientService
 * @Description None
 * @Date 2019/6/27 14:11
 * @Created by WDD
 */
public interface PatientService {
    PageBean<Patient> queryPage(Map<String, Object> paramMap);

    int deletePatient(List<Integer> ids);

    int addPatient(Patient patient);

    Patient findById(Integer sid);

    int editPatient(Patient patient);

    Patient findByPatient(Patient patient);

    boolean isPatientByHospitalId(Integer next);

    int editPswdByPatient(Patient patient);

    int findByName(String username);
}
