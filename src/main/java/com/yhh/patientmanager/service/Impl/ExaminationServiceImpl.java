package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.Examination;
import com.yhh.patientmanager.mapper.ExaminationMapper;
import com.yhh.patientmanager.service.ExaminationService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname ExaminationServiceImpl
 * @Description None
 * @Date 2019/6/29 20:09
 * @Created by WDD
 */
@Service
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private ExaminationMapper examinationMapper;

    @Override
    public PageBean<Examination> queryPage(Map<String, Object> paramMap) {
        PageBean<Examination> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Examination> datas = examinationMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = examinationMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int addExamination(Examination examination) {
        return examinationMapper.addExamination(examination);
    }

    @Override
    public int editExamination(Examination examination) {
        return examinationMapper.editExamination(examination);
    }

    @Override
    public int deleteExamination(List<Integer> ids) {
        return examinationMapper.deleteExamination(ids);
    }

    @Override
    public List<Examination> getExaminationById(List<Integer> ids) {
        return examinationMapper.getExaminationById(ids);
    }

    @Override
    public int findByName(String name) {
        return examinationMapper.findByName(name);
    }

}
