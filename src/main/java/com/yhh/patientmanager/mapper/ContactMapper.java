package com.yhh.patientmanager.mapper;


import com.yhh.patientmanager.domain.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ContactMapper {
    List<Contact> queryList(Map<String, Object> paramMap);
    Integer queryCount(Map<String, Object> paramMap);
}
