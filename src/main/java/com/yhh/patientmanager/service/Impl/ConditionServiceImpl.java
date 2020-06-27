package com.yhh.patientmanager.service.Impl;



import com.yhh.patientmanager.domain.Condition;
import com.yhh.patientmanager.mapper.ConditionMapper;
import com.yhh.patientmanager.mapper.ContactMapper;
import com.yhh.patientmanager.service.ConditionService;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ConditionServiceImpl implements ConditionService {
    @Autowired
    private ConditionMapper conditionMapper;

    @Override
    public PageBean<Condition> queryPage(Map<String, Object> paramMap) {
        PageBean<Condition> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Condition> datas = conditionMapper.queryList(paramMap);


        pageBean.setDatas(datas);

        Integer totalsize = conditionMapper.queryCount(paramMap);

        pageBean.setTotalsize(totalsize);
        return pageBean;
    }
}
