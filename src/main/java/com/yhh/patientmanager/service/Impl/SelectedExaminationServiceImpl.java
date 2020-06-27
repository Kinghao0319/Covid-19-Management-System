package com.yhh.patientmanager.service.Impl;

import com.yhh.patientmanager.domain.SelectedExamination;
import com.yhh.patientmanager.mapper.ExaminationMapper;
import com.yhh.patientmanager.mapper.SelectedExaminationMapper;
import com.yhh.patientmanager.service.SelectedExaminationService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Classname SelectedExaminationServiceImpl
 * @Description None
 * @Date 2019/6/30 10:48
 * @Created by WDD
 */
@Service
public class SelectedExaminationServiceImpl implements SelectedExaminationService {

    @Autowired
    private SelectedExaminationMapper selectedExaminationMapper;
    @Autowired
    private ExaminationMapper examinationMapper;

    @Override
    public PageBean<SelectedExamination> queryPage(Map<String, Object> paramMap) {
        PageBean<SelectedExamination> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<SelectedExamination> datas = selectedExaminationMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = selectedExaminationMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    @Transactional
    public int addSelectedExamination(SelectedExamination selectedExamination) {
        SelectedExamination s = selectedExaminationMapper.findBySelectedExamination(selectedExamination);
        if(StringUtils.isEmpty(s)){
            int count = examinationMapper.addPatientNum(selectedExamination.getExaminationId());
            if(count == 1){
                selectedExaminationMapper.addSelectedExamination(selectedExamination);
                return count;
            }
            if(count == 0){
                return count;
            }
        }else{
            return 2;
        }
        return 3;
    }

    @Override
    @Transactional
    public int deleteSelectedExamination(Integer id) {
        SelectedExamination selectedExamination = selectedExaminationMapper.findById(id);
        examinationMapper.deletePatientNum(selectedExamination.getExaminationId());
        return selectedExaminationMapper.deleteSelectedExamination(id);
    }

    @Override
    public boolean isPatientId(int id) {
        List<SelectedExamination> selectedExaminationList = selectedExaminationMapper.isPatientId(id);
        if (selectedExaminationList.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<SelectedExamination> getAllBySid(int patientid) {
        return selectedExaminationMapper.getAllBySid(patientid);
    }
}
