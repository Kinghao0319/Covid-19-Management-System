package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.SelectedExamination;
import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.service.SelectedExaminationService;
import com.yhh.patientmanager.util.AjaxResult;
import com.yhh.patientmanager.util.Const;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname SelectedExaminationController
 * @Description 预约体检信息控制器
 * @Date 2019/6/30 10:39
 * @Created by WDD
 */
@Controller
@RequestMapping("/selectedExamination")
public class SelectedExaminationController {

    @Autowired
    private SelectedExaminationService selectedExaminationService;



    @GetMapping("/selectedExamination_list")
    public String selectedExaminationList(){
        return "examination/selectedExaminationList";
    }

    /**
     * 异步加载预约体检信息列表
     * @param page
     * @param rows
     * @param patientid
     * @param examinationid
     * @param from
     * @return
     */
    @PostMapping("/getSelectedExaminationList")
    @ResponseBody
    public Object getHospitalList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                               @RequestParam(value = "doctorid", defaultValue = "0")String patientid,
                               @RequestParam(value = "doctorid", defaultValue = "0")String examinationid ,String from,HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!patientid.equals("0"))  paramMap.put("patientId",patientid);
        if(!examinationid.equals("0"))  paramMap.put("examinationId",examinationid);
        //判断是医生还是患者权限
        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
        if(!StringUtils.isEmpty(patient)){
            //是患者权限，只能查询自己的信息
            paramMap.put("patientid",patient.getId());
        }
        PageBean<SelectedExamination> pageBean = selectedExaminationService.queryPage(paramMap);
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
     * 患者进行预约体检
     * @param selectedExamination
     * @return
     */
    @PostMapping("/addSelectedExamination")
    @ResponseBody
    public AjaxResult addSelectedExamination(SelectedExamination selectedExamination){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = selectedExaminationService.addSelectedExamination(selectedExamination);
            if(count == 1){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("预约体检成功");
            }else if(count == 0){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("预约体检人数已满");
            }else if(count == 2){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("已预约择这项健康检测");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统内部出错，请联系管理员!");
        }
        return ajaxResult;
    }


    /**
     * 删除预约体检信息
     * @param id
     * @return
     */
    @PostMapping("/deleteSelectedExamination")
    @ResponseBody
    public AjaxResult deleteSelectedExamination(Integer id){
        AjaxResult ajaxResult = new AjaxResult();

        try {
            int count = selectedExaminationService.deleteSelectedExamination(id);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("移除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("移除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxResult;
    }


}
