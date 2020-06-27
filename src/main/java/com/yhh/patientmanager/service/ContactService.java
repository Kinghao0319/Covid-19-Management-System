package com.yhh.patientmanager.service;



import com.yhh.patientmanager.domain.Contact;
import com.yhh.patientmanager.util.PageBean;

import java.util.List;
import java.util.Map;

public interface ContactService {
    PageBean<Contact> queryPage(Map<String, Object> paramMap);

}
