
package com.yhh.patientmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/query")
public class QueryController {

    @RequestMapping("/query_list")
    public String queryList(){
        return "query/queryList";
    }
    @RequestMapping("/medicine")
    public String queryMedicine(){
        return "query/medicineQuery";
    }
    @RequestMapping("/contact")
    public String queryContact(){
        return "query/contactQuery";
    }
    @RequestMapping("/condition")
    public String queryArea(){
        return "query/conditionQuery";
    }
}
