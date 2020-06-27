package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Medicine;
import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.service.HospitalService;
import com.yhh.patientmanager.service.MedicineService;
import com.yhh.patientmanager.service.SelectedExaminationService;
import com.yhh.patientmanager.service.PatientService;
import com.yhh.patientmanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;


    @RequestMapping("/getMedicineList")
    @ResponseBody
    public Object getMedicineList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                 @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                 String patientName,
                                 @RequestParam(value = "hospitalid",required=false, defaultValue = "0")String hospitalid, String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();

        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
//        if(!StringUtils.isEmpty(patientName))  paramMap.put("username",patientName);
//        if(!hospitalid.equals("0"))  paramMap.put("hospitalid",hospitalid);


//        //判断是医生还是患者权限
//        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
//        if(!StringUtils.isEmpty(patient)){
//            //是患者权限，只能查询自己的信息
//            paramMap.put("patientid",patient.getId());
//        }

        PageBean<Medicine> pageBean = medicineService.queryPage(paramMap);
        if(!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else{
            Map<String,Object> result = new HashMap();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }

    @RequestMapping("/getHometownList")
    @ResponseBody
    public Object getHometownList( String from, HttpSession session){

        List<String> result=medicineService.queryDisHometown();
        return result;

//        if(!StringUtils.isEmpty(patientName))  paramMap.put("username",patientName);
//        if(!hospitalid.equals("0"))  paramMap.put("hospitalid",hospitalid);


//        //判断是医生还是患者权限
//        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
//        if(!StringUtils.isEmpty(patient)){
//            //是患者权限，只能查询自己的信息
//            paramMap.put("patientid",patient.getId());
//        }


    }

}
