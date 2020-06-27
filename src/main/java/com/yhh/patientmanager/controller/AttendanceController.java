package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Attendance;
import com.yhh.patientmanager.domain.Examination;
import com.yhh.patientmanager.domain.SelectedExamination;
import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.service.AttendanceService;
import com.yhh.patientmanager.service.ExaminationService;
import com.yhh.patientmanager.service.SelectedExaminationService;
import com.yhh.patientmanager.util.AjaxResult;
import com.yhh.patientmanager.util.Const;
import com.yhh.patientmanager.util.DateFormatUtil;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Classname AttendanceController
 * @Description None
 * @Date 2019/7/1 11:57
 * @Created by WDD
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SelectedExaminationService selectedExaminationService;
    @Autowired
    private ExaminationService examinationService;

    @GetMapping("/attendance_list")
    public String attendanceList(){
        return "attendance/attendanceList";
    }


    /**
     * 异步获取考勤列表数据
     * @param page
     * @param rows
     * @param patientid
     * @param examinationid
     * @param type
     * @param date
     * @param from
     * @param session
     * @return
     */
    @RequestMapping("/getAttendanceList")
    @ResponseBody
    public Object getAttendanceList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                 @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                 @RequestParam(value = "patientid", defaultValue = "0")String patientid,
                                 @RequestParam(value = "examinationid", defaultValue = "0")String examinationid,
                                 String type,String date, String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!patientid.equals("0"))  paramMap.put("patientid",patientid);
        if(!examinationid.equals("0"))  paramMap.put("examinationid",examinationid);
        if(!StringUtils.isEmpty(type))  paramMap.put("type",type);
        if(!StringUtils.isEmpty(date))  paramMap.put("date",date);

        //判断是医生还是患者权限
        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
        if(!StringUtils.isEmpty(patient)){
            //是患者权限，只能查询自己的信息
            paramMap.put("patientid",patient.getId());
        }
        PageBean<Attendance> pageBean = attendanceService.queryPage(paramMap);
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
     * 通过 预约体检信息中的健康检测id 查询 患者所选择的健康检测
     * @param patientid
     * @return
     */
    @RequestMapping("/getPatientSelectedExaminationList")
    @ResponseBody
    public Object getPatientSelectedExaminationList(@RequestParam(value = "patientid", defaultValue = "0")String patientid){
        //通过患者id 查询 预约体检信息
        List<SelectedExamination> selectedExaminationList = selectedExaminationService.getAllBySid(Integer.parseInt(patientid));
        //通过 预约体检信息中的健康检测id 查询 患者所选择的健康检测
        List<Integer> ids = new ArrayList<>();
        for(SelectedExamination selectedExamination : selectedExaminationList){
            ids.add(selectedExamination.getExaminationId());
        }
        List<Examination> examinationList = examinationService.getExaminationById(ids);
        return examinationList;
    }


    /**
     * 添加考勤签到
     * @param attendance
     * @return
     */
    @PostMapping("/addAttendance")
    @ResponseBody
    public AjaxResult addAttendance(Attendance attendance){
        AjaxResult ajaxResult = new AjaxResult();
        attendance.setDate(DateFormatUtil.getFormatDate(new Date(),"yyyy-MM-dd"));
        //判断是否已签到
        if(attendanceService.isAttendance(attendance)){
            //true为已签到
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("已签到，请勿重复签到！");
        }else{
            int count = attendanceService.addAtendance(attendance);
            if(count > 0){
                //签到成功
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("签到成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新签到");
            }
        }
        return ajaxResult;
    }

    /**
     * 删除考勤签到
     * @param id
     * @return
     */
    @PostMapping("/deleteAttendance")
    @ResponseBody
    public AjaxResult deleteAttendance(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = attendanceService.deleteAttendance(id);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常,请重试");
            e.printStackTrace();
        }
        return ajaxResult;
    }
}
