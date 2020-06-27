package com.yhh.patientmanager.service;

import com.yhh.patientmanager.domain.Admin;

/**
 * @Classname AdminService
 * @Description None
 * @Date 2019/6/25 11:07
 * @Created by WDD
 */
public interface AdminService {

    Admin findByAdmin(Admin admin);


    int editPswdByAdmin(Admin admin);
}
