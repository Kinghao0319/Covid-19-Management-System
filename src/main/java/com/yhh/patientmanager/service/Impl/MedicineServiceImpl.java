package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.Medicine;

import com.yhh.patientmanager.mapper.MedicineMapper;

import com.yhh.patientmanager.service.MedicineService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public PageBean<Medicine> queryPage(Map<String, Object> paramMap) {
        PageBean<Medicine> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Medicine> datas = medicineMapper.queryList(paramMap);


        pageBean.setDatas(datas);

        Integer totalsize = medicineMapper.queryCount(paramMap);

        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public List<String> queryDisHometown(){
        List<String> result=medicineMapper.queryDisHometown();
        return result;
    }
}
