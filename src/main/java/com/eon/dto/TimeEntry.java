package com.eon.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeEntry {
    private String id;
    private Timestamp startTime;
    private Timestamp endTime;
    private String userId;
    private String projectId;
    private String hoursTypeId;
}
