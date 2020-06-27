package com.yhh.patientmanager.interceptors;

import com.yhh.patientmanager.domain.Admin;
import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.domain.Doctor;
import com.yhh.patientmanager.util.Const;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname LoginInterceptor
 * @Description 登录拦截器
 * @Created by WDD
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Admin user = (Admin)request.getSession().getAttribute(Const.ADMIN);
        Doctor doctor = (Doctor)request.getSession().getAttribute(Const.DOCTOR);
        Patient patient = (Patient)request.getSession().getAttribute(Const.PATIENT);
        if(!StringUtils.isEmpty(user) || !StringUtils.isEmpty(doctor) || !StringUtils.isEmpty(patient)){
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/system/login");
        return false;
    }

}
