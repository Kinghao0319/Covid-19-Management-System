package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.Contact;
import com.yhh.patientmanager.domain.Medicine;
import com.yhh.patientmanager.mapper.ContactMapper;
import com.yhh.patientmanager.mapper.MedicineMapper;
import com.yhh.patientmanager.service.ContactService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public PageBean<Contact> queryPage(Map<String, Object> paramMap) {
        PageBean<Contact> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Contact> datas = contactMapper.queryList(paramMap);


        pageBean.setDatas(datas);

        Integer totalsize = contactMapper.queryCount(paramMap);

        pageBean.setTotalsize(totalsize);
        return pageBean;
    }


}
