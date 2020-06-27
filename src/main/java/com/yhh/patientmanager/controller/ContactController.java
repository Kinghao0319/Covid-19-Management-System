package com.yhh.patientmanager.controller;


import com.yhh.patientmanager.domain.Contact;
import com.yhh.patientmanager.service.ContactService;

import com.yhh.patientmanager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping("/getContactList")
    @ResponseBody
    public Object getContactList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                  @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                  String patientName,
                                  @RequestParam(value = "hospitalid",required=false, defaultValue = "0")String hospitalid, String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();

        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);


        PageBean<Contact> pageBean = contactService.queryPage(paramMap);
        if(!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else{
            Map<String,Object> result = new HashMap();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }
}
