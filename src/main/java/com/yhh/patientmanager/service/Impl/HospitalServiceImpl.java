package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.Hospital;
import com.yhh.patientmanager.mapper.HospitalMapper;
import com.yhh.patientmanager.service.HospitalService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Classname HospitalServiceImpl
 * @Description None
 * @Date 2019/6/26 10:14
 * @Created by WDD
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    @Override
    public PageBean<Hospital> queryPage(Map<String, Object> paramMap) {
        PageBean<Hospital> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Hospital> datas = hospitalMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = hospitalMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int addHospital(Hospital hospital) {
        return hospitalMapper.addHospital(hospital);
    }

    @Override
    @Transactional
    public int deleteHospital(List<Integer> ids) {
        return hospitalMapper.deleteHospital(ids);
    }

    @Override
    public int editHospital(Hospital hospital) {
        return hospitalMapper.editHospital(hospital);
    }

    @Override
    public Hospital findByName(String hospitalName) {
        return hospitalMapper.findByName(hospitalName);
    }

}
