package com.yhh.patientmanager.util;

/**
 * @Claspnumame PnumGenerateUtil
 * @Description 随机生成学号
 * @Date 2019/6/27 20:17
 * @Created by WDD
 */
public class PnumGenerateUtil {
    public static String generatePnum(int hospitalId){
        String pnum = "";
        pnum = "S" + hospitalId + System.currentTimeMillis();
        return pnum;
    }
    public static String generateDoctorPnum(int hospitalId){
        String pnum = "";
        pnum = "T" + hospitalId + System.currentTimeMillis();
        return pnum;
    }
}
