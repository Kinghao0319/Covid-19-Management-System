package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Hospital;
import com.yhh.patientmanager.service.HospitalService;
import com.yhh.patientmanager.service.PatientService;
import com.yhh.patientmanager.util.AjaxResult;
import com.yhh.patientmanager.util.Data;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Classname HospitalController
 * @Description 医院管理
 * @Date 2019/6/26 9:08
 * @Created by WDD
 */
@Controller
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private PatientService patientService;

    /**
     * 跳转医院页面
     * @return
     */
    @GetMapping("/hospital_list")
    public String hospitalList(){
        return "hospital/hospitalList";
    }

    /**
     * 异步加载医院列表
     * @param page
     * @param rows
     * @param hospitalName
     * @return
     */
    @PostMapping("/getHospitalList")
    @ResponseBody
    public Object getHospitalList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "100")Integer rows, String hospitalName, String from){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!StringUtils.isEmpty(hospitalName))  paramMap.put("name",hospitalName);
        PageBean<Hospital> pageBean = hospitalService.queryPage(paramMap);
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
     * 添加医院
     * @param hospital
     * @return
     */
    @PostMapping("/addHospital")
    @ResponseBody
    public AjaxResult addHospital(Hospital hospital){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = hospitalService.addHospital(hospital);
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
     * 删除医院
     * @param data
     * @return
     */
    @PostMapping("/deleteHospital")
    @ResponseBody
    public AjaxResult deleteHospital(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            List<Integer> ids = data.getIds();
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()){  //判断是否存在健康检测关联患者
                if(!patientService.isPatientByHospitalId(iterator.next())){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("无法删除,医院下存在患者");
                    return ajaxResult;
                }
            }
            int count = hospitalService.deleteHospital(data.getIds());
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

    /**
     * 医院修改
     * @param hospital
     * @return
     */
    @PostMapping("/editHospital")
    @ResponseBody
    public AjaxResult editHospital(Hospital hospital){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = hospitalService.editHospital(hospital);
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
}
