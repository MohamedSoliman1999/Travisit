package com.travisit.travisitstandard.model;

import java.io.Serializable;

public class Tour implements Serializable {
    private Integer id = null;
    private String title = null;
    private String program = null;
    private Double hourRate = 0.0;
    private String startTime = null;
    private String endTime = null;
    private String updatedAt = null;
    private String createdAt = null;
    private Boolean isCompleted = null;
    private Boolean type=null;
    public Tour(String title, String program, Double hourRate, String startTime, String endTime, Boolean isCompleted) {
        this.title = title;
        this.program = program;
        this.hourRate = hourRate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCompleted = isCompleted;
    }
    public Tour(Integer id, String title, String program, Double hourRate, String startTime, String endTime, String updatedAt, String createdAt, Boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.program = program;
        this.hourRate = hourRate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.isCompleted = isCompleted;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getProgram() {
        return program;
    }
    public void setProgram(String program) {
        this.program = program;
    }
    public Double getHourRate() {
        return hourRate;
    }
    public void setHourRate(Double hourRate) {
        this.hourRate = hourRate;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}