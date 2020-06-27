package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.Doctor;
import com.yhh.patientmanager.mapper.DoctorMapper;
import com.yhh.patientmanager.service.DoctorService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname DoctorServiceImpl
 * @Description None
 * @Date 2019/6/28 18:56
 * @Created by WDD
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public PageBean<Doctor> queryPage(Map<String, Object> paramMap) {
        PageBean<Doctor> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Doctor> datas = doctorMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = doctorMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int deleteDoctor(List<Integer> ids) {
        return doctorMapper.deleteDoctor(ids);
    }

    @Override
    public int addDoctor(Doctor doctor) {
        return doctorMapper.addDoctor(doctor);
    }

    @Override
    public Doctor findById(Integer tid) {
        return doctorMapper.findById(tid);
    }

    @Override
    public int editDoctor(Doctor doctor) {
        return doctorMapper.editDoctor(doctor);
    }

    @Override
    public Doctor findByDoctor(Doctor doctor) {
        return doctorMapper.findByDoctor(doctor);
    }

    @Override
    public int editPswdByDoctor(Doctor doctor) {
        return doctorMapper.editPswdByDoctor(doctor);
    }
}
