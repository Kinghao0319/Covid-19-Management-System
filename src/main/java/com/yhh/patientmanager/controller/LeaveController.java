package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Leave;
import com.yhh.patientmanager.service.LeaveService;
import com.yhh.patientmanager.util.AjaxResult;
import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LeaveController
 * @Description 出院控制器
 * @Date 2019/7/2 15:43
 * @Created by WDD
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @RequestMapping("/leave_list")
    public String leaveList(){
        return "leave/leaveList";
    }

    /**
     * 异步加载出院列表
     * @param page
     * @param rows
     * @param patientid
     * @param from
     * @return
     */
    @PostMapping("/getLeaveList")
    @ResponseBody
    public Object getHospitalList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                               @RequestParam(value = "patientid", defaultValue = "0")String patientid,
                               String from){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!patientid.equals("0"))  paramMap.put("patientId",patientid);
        PageBean<Leave> pageBean = leaveService.queryPage(paramMap);
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
     * 添加患者出院条
     * @param leave
     * @return
     */
    @PostMapping("/addLeave")
    @ResponseBody
    public AjaxResult addLeave(Leave leave){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.addLeave(leave);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("添加成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }

    /**
     * 修改出院条
     * @param leave
     * @return
     */
    @PostMapping("/editLeave")
    @ResponseBody
    public AjaxResult editLeave(Leave leave){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.editLeave(leave);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }

    /**
     * 对假条进行审核
     * @param leave
     * @return
     */
    @PostMapping("/checkLeave")
    @ResponseBody
    public AjaxResult checkLeave(Leave leave){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.checkLeave(leave);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("审批成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("审批失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }


    /**
     * 删除假条
     * @param id
     * @return
     */
    @PostMapping("/deleteLeave")
    @ResponseBody
    public AjaxResult deleteLeave(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = leaveService.deleteLeave(id);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }


}
