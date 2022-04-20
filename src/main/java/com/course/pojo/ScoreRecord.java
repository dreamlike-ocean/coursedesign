package com.course.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRecord {
    private Integer recordId;
    private Integer userId;
    private Integer count;
    private Long timestamp;
    private Integer type;

    public ScoreRecord(Integer userId, Integer count,Integer type) {
        this.userId = userId;
        this.count = count;
        this.type = type;
        timestamp = System.currentTimeMillis();
    }

    public ScoreRecord(Integer userId, Integer count, Long timestamp, Integer type) {
        this.userId = userId;
        this.count = count;
        this.timestamp = timestamp;
        this.type = type;
    }


}
