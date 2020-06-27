package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MedicineMapper {
    @Select("select s_medicine.id,patient_id,username as patient_name,hometown as patient_hometown,medicine_name,if_use,start_time from s_medicine,s_patient where s_patient.id=patient_id limit #{startIndex},#{pagesize}")
    List<Medicine> queryList(Map<String, Object> paramMap);

    @Select("select count(*) from s_medicine")
    Integer queryCount(Map<String, Object> paramMap);

    @Select("select distinct hometown from s_patient")
    List<String> queryDisHometown();
}
