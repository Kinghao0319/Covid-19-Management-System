package com.yhh.patientmanager.mapper;

import com.yhh.patientmanager.domain.Score;
import com.yhh.patientmanager.domain.ScoreStats;

import java.util.List;
import java.util.Map;

/**
 * @Classname ScoreMapper
 * @Description None
 * @Date 2019/7/3 11:47
 * @Created by WDD
 */
public interface ScoreMapper {
    List<Score> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addScore(Score score);

    Score isScore(Score score);

    int editScore(Score score);

    int deleteScore(Integer id);

    List<Score> getAll(Score score);

    ScoreStats getAvgStats(Integer examinationid);
}
