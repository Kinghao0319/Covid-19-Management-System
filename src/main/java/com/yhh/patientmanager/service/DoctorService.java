package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Doctor;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname DoctorService
 * @Description None
 * @Date 2019/6/28 18:56
 * @Created by WDD
 */
public interface DoctorService {
    PageBean<Doctor> queryPage(Map<String, Object> paramMap);

    int deleteDoctor(List<Integer> ids);

    int addDoctor(Doctor doctor);

    Doctor findById(Integer tid);

    int editDoctor(Doctor doctor);

    Doctor findByDoctor(Doctor doctor);

    int editPswdByDoctor(Doctor doctor);
}
