package com.yhh.patientmanager.controller;

import com.yhh.patientmanager.domain.Score;
import com.yhh.patientmanager.domain.ScoreStats;
import com.yhh.patientmanager.domain.Patient;
import com.yhh.patientmanager.service.ExaminationService;
import com.yhh.patientmanager.service.ScoreService;
import com.yhh.patientmanager.service.SelectedExaminationService;
import com.yhh.patientmanager.service.PatientService;
import com.yhh.patientmanager.util.AjaxResult;
import com.yhh.patientmanager.util.Const;
import com.yhh.patientmanager.util.PageBean;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ScoreController
 * @Description 健康评分控制器
 * @Date 2019/7/3 11:38
 * @Created by WDD
 */
@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private SelectedExaminationService selectedExaminationService;


    @GetMapping("/score_list")
    public String scoreList(){
        return "score/scoreList";
    }


    /**
     * 异步加载健康评分数据列表
     * @param page
     * @param rows
     * @param patientid
     * @param examinationid
     * @param from
     * @param session
     * @return
     */
    @RequestMapping("/getScoreList")
    @ResponseBody
    public Object getScoreList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                    @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                    @RequestParam(value = "patientid", defaultValue = "0")String patientid,
                                    @RequestParam(value = "examinationid", defaultValue = "0")String examinationid,
                                    String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!patientid.equals("0"))  paramMap.put("patientid",patientid);
        if(!examinationid.equals("0"))  paramMap.put("examinationid",examinationid);

        //判断是医生还是患者权限
        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
        if(!StringUtils.isEmpty(patient)){
            //是患者权限，只能查询自己的信息
            paramMap.put("patientid",patient.getId());
        }
        PageBean<Score> pageBean = scoreService.queryPage(paramMap);
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
     * 添加健康评分
     * @param score
     * @return
     */
    @PostMapping("/addScore")
    @ResponseBody
    public AjaxResult addScore(Score score){
        AjaxResult ajaxResult = new AjaxResult();
        //判断是否已录入健康评分
        if(scoreService.isScore(score)){
            //true为已签到
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("已录入，请勿重复录入！");
        }else{
            int count = scoreService.addScore(score);
            if(count > 0){
                //签到成功
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("录入成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新录入");
            }
        }
        return ajaxResult;
    }


    /**
     * 修改患者健康评分
     * @param score
     * @return
     */
    @PostMapping("/editScore")
    @ResponseBody
    public AjaxResult editScore(Score score){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = scoreService.editScore(score);
            if(count > 0){
                //签到成功
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新修改");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统错误，请重新修改");
        }
        return ajaxResult;
    }

    /**
     * 删除患者健康评分
     * @param id
     * @return
     */
    @PostMapping("/deleteScore")
    @ResponseBody
    public AjaxResult deleteScore(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = scoreService.deleteScore(id);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统错误，请重新删除");
        }
        return ajaxResult;
    }

    /**
     * 导入xlsx表 并存入数据库
     * @param importScore
     * @param response
     */
    @PostMapping("/importScore")
    @ResponseBody
    public void importScore(@RequestParam("importScore") MultipartFile importScore, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try {
            InputStream inputStream = importScore.getInputStream();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            int count = 0;
            String errorMsg = "";
            for(int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++){
                XSSFRow row = sheetAt.getRow(rowNum); // 获取第rowNum行
                //第0列
                XSSFCell cell = row.getCell(0); // 获取第rowNum行的第0列 即坐标（rowNum，0）
                if(cell == null){
                    errorMsg += "第" + rowNum + "行患者缺失！\n";
                    continue;
                }
                //第1列
                cell = row.getCell(1);
                if(cell == null){
                    errorMsg += "第" + rowNum + "行健康检测缺失！\n";
                    continue;
                }
                //第2列
                cell = row.getCell(2);
                if(cell == null){
                    errorMsg += "第" + rowNum + "行健康评分缺失！\n";
                    continue;
                }
                double scoreValue = cell.getNumericCellValue();
                //第3列
                cell = row.getCell(3);
                String remark = null;
                if(cell != null){
                    remark = cell.getStringCellValue();
                }

                //将患者，健康检测转换为id,存入数据库
                // 1)首先获取对应的id
                int patientId = patientService.findByName(row.getCell(0).getStringCellValue());
                int examinationId = examinationService.findByName(row.getCell(1).getStringCellValue());
                // 2)判断是否已存在数据库中
                Score score = new Score();
                score.setPatientId(patientId);
                score.setExaminationId(examinationId);
                score.setScore(scoreValue);
                score.setRemark(remark);
                if(!scoreService.isScore(score)){
                    // 3)存入数据库
                    int i = scoreService.addScore(score);
                    if(i > 0){
                        count ++ ;
                    }
                }else{
                    errorMsg += "第" + rowNum + "行已录入，不重复录入！\n";
                }
            }
            errorMsg += "成功录入" + count + "条健康评分！";
            response.getWriter().write("<div id='message'>"+errorMsg+"</div>");

        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.getWriter().write("<div id='message'>上传错误</div>");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    /**
     * 导出xlsx表
     * @param response
     * @param score
     * @param session
     */
    @RequestMapping("/exportScore")
    @ResponseBody
    private void exportScore(HttpServletResponse response,Score score,HttpSession session) {
        //获取当前登录用户类型
        Patient patient = (Patient) session.getAttribute(Const.PATIENT);
        if(!StringUtils.isEmpty(patient)){
            //如果是患者，只能查看自己的信息
            score.setPatientId(patient.getId());
        }
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("score_list_sid_"+score.getPatientId()+"_cid_"+score.getPatientId()+".xls", "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            ServletOutputStream outputStream = response.getOutputStream();
            List<Score> scoreList = scoreService.getAll(score);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet createSheet = xssfWorkbook.createSheet("健康评分列表");
            XSSFRow createRow = createSheet.createRow(0);
            createRow.createCell(0).setCellValue("患者");
            createRow.createCell(1).setCellValue("健康检测");
            createRow.createCell(2).setCellValue("健康评分");
            createRow.createCell(3).setCellValue("备注");
            //实现将数据装入到excel文件中
            int row = 1;
            for( Score s:scoreList){
                createRow = createSheet.createRow(row++);
                createRow.createCell(0).setCellValue(s.getPatientName());
                createRow.createCell(1).setCellValue(s.getExaminationName());
                createRow.createCell(2).setCellValue(s.getScore());
                createRow.createCell(3).setCellValue(s.getRemark());
            }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转统计页面
     * @return
     */
    @RequestMapping("/scoreStats")
    public String scoreStats(){
        return "score/scoreStats";
    }


    /**
     * 统计健康评分数据
     * @param examinationid
     * @param searchType
     * @return
     */
    @RequestMapping("/getScoreStatsList")
    @ResponseBody
    public Object getScoreStatsList(@RequestParam(value = "examinationid", defaultValue = "0")Integer examinationid,
                                        String searchType){
        AjaxResult ajaxResult = new AjaxResult();
        if(searchType.equals("avg")){
            ScoreStats scoreStats = scoreService.getAvgStats(examinationid);

            List<Double> scoreList = new ArrayList<Double>();
            scoreList.add(scoreStats.getMax_score());
            scoreList.add(scoreStats.getMin_score());
            scoreList.add(scoreStats.getAvg_score());

            List<String> avgStringList = new ArrayList<String>();
            avgStringList.add("最高分");
            avgStringList.add("最低分");
            avgStringList.add("平均分");

            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("examinationName", scoreStats.getExaminationName());
            retMap.put("scoreList", scoreList);
            retMap.put("avgList", avgStringList);
            retMap.put("type", "success");

            return retMap;
        }

        Score score = new Score();
        score.setExaminationId(examinationid);
        List<Score> scoreList = scoreService.getAll(score);


        List<Integer> numberList = new ArrayList<Integer>();
        numberList.add(0);
        numberList.add(0);
        numberList.add(0);
        numberList.add(0);
        numberList.add(0);

        List<String> rangeStringList = new ArrayList<String>();
        rangeStringList.add("60分以下");
        rangeStringList.add("60~70分");
        rangeStringList.add("70~80分");
        rangeStringList.add("80~90分");
        rangeStringList.add("90~100分");

        String examinationName = "";

        for(Score sc : scoreList){
            examinationName = sc.getExaminationName();  //获取健康检测名
            double scoreValue = sc.getScore();//获取健康评分
            if(scoreValue < 60){
                numberList.set(0, numberList.get(0)+1);
                continue;
            }
            if(scoreValue <= 70 && scoreValue >= 60){
                numberList.set(1, numberList.get(1)+1);
                continue;
            }
            if(scoreValue <= 80 && scoreValue > 70){
                numberList.set(2, numberList.get(2)+1);
                continue;
            }
            if(scoreValue <= 90 && scoreValue > 80){
                numberList.set(3, numberList.get(3)+1);
                continue;
            }
            if(scoreValue <= 100 && scoreValue > 90){
                numberList.set(4, numberList.get(4)+1);
                continue;
            }
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("examinationName", examinationName);
        retMap.put("numberList", numberList);
        retMap.put("rangeList", rangeStringList);
        retMap.put("type", "success");
        return retMap;
    }

}
