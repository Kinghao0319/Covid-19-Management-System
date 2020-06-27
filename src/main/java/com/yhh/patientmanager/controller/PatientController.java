package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.service.HospitalService;
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

/**
 * @Claspnumame PatientController
 * @Description None
 * @Date 2019/6/25 20:00
 * @Created by WDD
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private SelectedExaminationService selectedExaminationService;

    /**
     * 跳转患者信息页面
     * @return
     */
    @GetMapping("/patient_list")
    public String patientList(){
        return "patient/patientList";
    }

    /**
     * 异步加载患者信息
     * @param page
     * @param rows
     * @param patientName
     * @param hospitalid
     * @param from
     * @param session
     * @return
     */
    @RequestMapping("/getPatientList")
    @ResponseBody
    public Object getPatientList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                 @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                 String patientName,
                                 @RequestParam(value = "hospitalid", defaultValue = "0")String hospitalid, String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!StringUtils.isEmpty(patientName))  paramMap.put("username",patientName);
        if(!hospitalid.equals("0"))  paramMap.put("hospitalid",hospitalid);

        //判断是医生还是患者权限
        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
        if(!StringUtils.isEmpty(patient)){
            //是患者权限，只能查询自己的信息
            paramMap.put("patientid",patient.getId());
        }

        PageBean<Patient> pageBean = patientService.queryPage(paramMap);
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
     * 删除患者
     * @param data
     * @return
     */
    @PostMapping("/deletePatient")
    @ResponseBody
    public AjaxResult deletePatient(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            List<Integer> ids = data.getIds();
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()){  //判断是否存在健康检测关联患者
                if(!selectedExaminationService.isPatientId(iterator.next())){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("无法删除,存在健康检测关联患者");
                    return ajaxResult;
                }
            }
            File fileDir = UploadUtil.getImgDirFile();
            for(Integer id : ids){
                Patient byId = patientService.findById(id);
                if(!byId.getPhoto().isEmpty()){
                    File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                    if(file != null){
                        file.delete();
                    }
                }
            }
            int count = patientService.deletePatient(ids);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("全部删除成功");

            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }


    /**
     * 添加患者
     * @param files
     * @param patient
     * @return
     * @throws IOException
     */
    @RequestMapping("/addPatient")
    @ResponseBody
    public AjaxResult addPatient(@RequestParam("file") MultipartFile[] files,Patient patient) throws IOException {

        AjaxResult ajaxResult = new AjaxResult();
        patient.setPnum(PnumGenerateUtil.generatePnum(patient.getHospitalId()));

        // 存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        for(MultipartFile fileImg : files){

            // 拿到文件名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            String uuidName = UUID.randomUUID().toString();

            try {
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator +uuidName+ extName);

                // 上传图片到 -》 “绝对路径”
                fileImg.transferTo(newFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            patient.setPhoto(uuidName+extName);
        }
        //保存患者信息到数据库
        try{
            int count = patientService.addPatient(patient);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("保存成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("保存失败");
        }

        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 修改患者信息
     * @param files
     * @param patient
     * @return
     */
    @PostMapping("/editPatient")
    @ResponseBody
    public AjaxResult editPatient(@RequestParam("file") MultipartFile[] files,Patient patient){
        AjaxResult ajaxResult = new AjaxResult();

        // 存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        for(MultipartFile fileImg : files){

            String name = fileImg.getOriginalFilename();
            if(name.equals("")){
                break;
            }

            // 拿到文件名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            String uuidName = UUID.randomUUID().toString();

            try {
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator +uuidName+ extName);
                // 上传图片到 -》 “绝对路径”
                System.out.println(fileDir.getAbsolutePath());
                fileImg.transferTo(newFile);

                Patient byId = patientService.findById(patient.getId());
                File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                if(file != null){
                    file.delete();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            patient.setPhoto(uuidName+extName);
        }

        try{
            int count = patientService.editPatient(patient);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("修改失败");
        }
        return ajaxResult;
    }
}
