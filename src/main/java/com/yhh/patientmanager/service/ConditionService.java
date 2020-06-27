package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Condition;

import com.yhh.patientmanager.util.PageBean;

import java.util.Map;

public interface ConditionService {
    PageBean<Condition> queryPage(Map<String, Object> paramMap);
}
