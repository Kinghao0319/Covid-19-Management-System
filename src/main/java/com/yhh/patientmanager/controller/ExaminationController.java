package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Examination;
import com.yhh.patientmanager.service.ExaminationService;
import com.yhh.patientmanager.util.AjaxResult;
import com.yhh.patientmanager.util.Data;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ExaminationController
 * @Description None
 * @Date 2019/6/29 20:02
 * @Created by WDD
 */
@Controller
@RequestMapping("/examination")
public class ExaminationController {

    @Autowired
    private ExaminationService examinationService;

    @GetMapping("/examination_list")
    public String examinationList(){
        return "examination/examinationList";
    }

    /**
     * 异步加载健康检测信息列表
     * @param page
     * @param rows
     * @param name
     * @param doctorid
     * @param from
     * @return
     */
    @PostMapping("/getExaminationList")
    @ResponseBody
    public Object getExaminationList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "500")Integer rows,
                               String name,
                               @RequestParam(value = "doctorid", defaultValue = "0")String doctorid ,String from){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!StringUtils.isEmpty(name))  paramMap.put("name",name);
        if(!doctorid.equals("0"))  paramMap.put("doctorId",doctorid);
        PageBean<Examination> pageBean = examinationService.queryPage(paramMap);
        if(!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else{
            Map<String,Object> result = new HashMap();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }

    /**
     * 添加健康检测信息
     * @param examination
     * @return
     */
    @PostMapping("/addExamination")
    @ResponseBody
    public AjaxResult addExamination(Examination examination){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = examinationService.addExamination(examination);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("添加成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("添加失败");
        }
        return ajaxResult;
    }


    /**
     * 修改健康检测信息
     * @param examination
     * @return
     */
    @PostMapping("/editExamination")
    @ResponseBody
    public AjaxResult editExamination(Examination examination){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = examinationService.editExamination(examination);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("修改失败");
        }
        return ajaxResult;
    }


    @PostMapping("/deleteExamination")
    @ResponseBody
    public AjaxResult deleteExamination(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = examinationService.deleteExamination(data.getIds());
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败,该医院存在医生或患者");
        }
        return ajaxResult;
    }
}
