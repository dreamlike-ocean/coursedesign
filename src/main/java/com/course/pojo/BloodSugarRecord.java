package com.course.pojo;

import lombok.Data;

@Data
public class BloodSugarRecord {
    private Integer recordId;
    private Integer userId;
    private String record;
    private Long timestamp;

    public BloodSugarRecord() {
    }

    public BloodSugarRecord(Integer userId, String record) {
        this.userId = userId;
        this.record = record;
        timestamp = System.currentTimeMillis();
    }
}
