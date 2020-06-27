package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Doctor;
import com.yhh.patientmanager.service.DoctorService;
import com.yhh.patientmanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Clasdnumame DoctorController
 * @Description None
 * @Date 2019/6/28 18:49
 * @Created by WDD
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @RequestMapping("/doctor_list")
    public String doctorList(){
        return "doctor/doctorList";
    }

    /**
     * 异步加载医生数据列表
     * @param page
     * @param rows
     * @param doctorName
     * @param hospitalid
     * @param from
     * @param session
     * @return
     */
    @PostMapping("/getDoctorList")
    @ResponseBody
    public Object getDoctorList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                 @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                 String doctorName,
                                 @RequestParam(value = "hospitalid", defaultValue = "0")String hospitalid, String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!StringUtils.isEmpty(doctorName))  paramMap.put("username",doctorName);
        if(!hospitalid.equals("0"))  paramMap.put("hospitalid",hospitalid);

        //判断是医生还是患者权限
        Doctor doctor = (Doctor) session.getAttribute(Const.DOCTOR);
        if(!StringUtils.isEmpty(doctor)){
            //是医生权限，只能查询自己的信息
            paramMap.put("doctorid",doctor.getId());
        }

        PageBean<Doctor> pageBean = doctorService.queryPage(paramMap);
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
     * 删除医生
     * @param data
     * @return
     */
    @PostMapping("/deleteDoctor")
    @ResponseBody
    public AjaxResult deleteDoctor(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            File fileDir = UploadUtil.getImgDirFile();
            for(Integer id : data.getIds()){
                Doctor byId = doctorService.findById(id);
                if(!byId.getPhoto().isEmpty()){
                    File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                    if(file != null){
                        file.delete();
                    }
                }
            }
            int count = doctorService.deleteDoctor(data.getIds());
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
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }

    /**
     * 添加医生
     * @param files
     * @param doctor
     * @return
     * @throws IOException
     */
    @RequestMapping("/addDoctor")
    @ResponseBody
    public AjaxResult addDoctor(@RequestParam("file") MultipartFile[] files, Doctor doctor) throws IOException {

        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(doctor.getHospitalId());
        System.out.println(DnumGenerateUtil.generateDoctorDnum(doctor.getHospitalId()));
        doctor.setDnum(DnumGenerateUtil.generateDoctorDnum(doctor.getHospitalId()));

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
            doctor.setPhoto(uuidName+extName);
        }
        //保存医生信息到数据库
        try{
            int count = doctorService.addDoctor(doctor);
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

    @PostMapping("/editDoctor")
    @ResponseBody
    public AjaxResult editDoctor(@RequestParam("file") MultipartFile[] files,Doctor doctor){
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
                fileImg.transferTo(newFile);

                Doctor byId = doctorService.findById(doctor.getId());
                File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                if(file != null){
                    file.delete();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            doctor.setPhoto(uuidName+extName);
        }

        try{
            int count = doctorService.editDoctor(doctor);
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
