package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.Hospital;

import java.util.List;
import java.util.Map;

/**
 * @Classname HospitalMapper
 * @Description None
 * @Date 2019/6/24 20:09
 * @Created by WDD
 */
public interface HospitalMapper {
    List<Hospital> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addHospital(Hospital hospital);

    int deleteHospital(List<Integer> ids);

    int editHospital(Hospital hospital);

    Hospital findByName(String hospitalName);
}
