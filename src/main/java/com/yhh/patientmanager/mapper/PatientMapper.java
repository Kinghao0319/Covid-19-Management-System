package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.Patient;

import java.util.List;
import java.util.Map;

/**
 * @Claspnumame PatientMapper
 * @Description None
 * @Date 2019/6/24 20:09
 * @Created by WDD
 */
public interface PatientMapper {
    List<Patient> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int deletePatient(List<Integer> ids);

    int addPatient(Patient patient);

    Patient findById(Integer sid);

    int editPatient(Patient patient);

    Patient findByPatient(Patient patient);

    List<Patient> isPatientByHospitalId(Integer id);

    int editPswdByPatient(Patient patient);

    int findByName(String name);
}
