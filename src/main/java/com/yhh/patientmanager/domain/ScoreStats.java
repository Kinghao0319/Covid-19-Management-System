package com.yhh.patientmanager.domain;

/**
 * @Classname ScoreStats
 * @Description 健康评分统计
 * @Date 2019/7/4 13:30
 * @Created by WDD
 */
public class ScoreStats {
    private Double max_score;
    private Double avg_score;
    private Double min_score;
    private String examinationName;//健康检测名称

    public Double getMax_score() {
        return max_score;
    }

    public void setMax_score(Double max_score) {
        this.max_score = max_score;
    }

    public Double getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(Double avg_score) {
        this.avg_score = avg_score;
    }

    public Double getMin_score() {
        return min_score;
    }

    public void setMin_score(Double min_score) {
        this.min_score = min_score;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }
}
