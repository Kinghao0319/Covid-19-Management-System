package com.yhh.patientmanager.util;

/**
 * @Clasdnumame DnumGenerateUtil
 * @Description 随机生成学号
 * @Date 2019/6/27 20:17
 * @Created by WDD
 */
public class DnumGenerateUtil {
    public static String generateDnum(int hospitalId){
        String dnum = "";
        dnum = "S" + hospitalId + System.currentTimeMillis();
        return dnum;
    }
    public static String generateDoctorDnum(int hospitalId){
        String dnum = "";
        dnum = "T" + hospitalId + System.currentTimeMillis();
        return dnum;
    }
}
