package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Hospital;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

/**
 * @Classname HospitalService
 * @Description None
 * @Date 2019/6/26 10:14
 * @Created by WDD
 */
public interface HospitalService {
    PageBean<Hospital> queryPage(Map<String, Object> paramMap);

    int addHospital(Hospital hospital);

    int deleteHospital(List<Integer> ids);

    int editHospital(Hospital hospital);

    Hospital findByName(String hospitalName);

}
