package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Medicine;
import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

public interface MedicineService {
    PageBean<Medicine> queryPage(Map<String, Object> paramMap);
    List<String> queryDisHometown();
}
