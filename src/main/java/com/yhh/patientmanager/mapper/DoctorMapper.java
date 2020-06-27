package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.Doctor;

import java.util.List;
import java.util.Map;

/**
 * @Classname DoctorMapper
 * @Description None
 * @Date 2019/6/28 19:06
 * @Created by WDD
 */
public interface DoctorMapper {
    List<Doctor> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int deleteDoctor(List<Integer> ids);

    int addDoctor(Doctor doctor);

    Doctor findById(Integer tid);

    int editDoctor(Doctor doctor);

    Doctor findByDoctor(Doctor doctor);

    int editPswdByDoctor(Doctor doctor);
}
