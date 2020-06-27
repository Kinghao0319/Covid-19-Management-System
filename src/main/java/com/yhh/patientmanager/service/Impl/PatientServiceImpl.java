package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.mapper.PatientMapper;
import com.yhh.patientmanager.service.PatientService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Claspnumame PatientServiceImpl
 * @Description None
 * @Date 2019/6/27 14:12
 * @Created by WDD
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public PageBean<Patient> queryPage(Map<String, Object> paramMap) {
        PageBean<Patient> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Patient> datas = patientMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = patientMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int deletePatient(List<Integer> ids) {
        return patientMapper.deletePatient(ids);
    }

    @Override
    public int addPatient(Patient patient) {
        return patientMapper.addPatient(patient);
    }

    @Override
    public Patient findById(Integer sid) {
        return patientMapper.findById(sid);
    }

    @Override
    public int editPatient(Patient patient) {
        return patientMapper.editPatient(patient);
    }

    @Override
    public Patient findByPatient(Patient patient) {
        return patientMapper.findByPatient(patient);
    }

    @Override
    public boolean isPatientByHospitalId(Integer id) {
        List<Patient> patientList = patientMapper.isPatientByHospitalId(id);
        if (patientList.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int editPswdByPatient(Patient patient) {
        return patientMapper.editPswdByPatient(patient);
    }

    @Override
    public int findByName(String name) {
        return patientMapper.findByName(name);
    }
}
