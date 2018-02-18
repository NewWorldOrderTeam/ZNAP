package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HoursChooserAPI {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("CountJobs")
    @Expose
    private Integer countJobs;
    @SerializedName("CountJobsAllow")
    @Expose
    private Integer countJobsAllow;
    @SerializedName("IsAllow")
    @Expose
    private Integer isAllow;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("StopTime")
    @Expose
    private String stopTime;
    @SerializedName("TimeType")
    @Expose
    private Integer timeType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCountJobs() {
        return countJobs;
    }

    public void setCountJobs(Integer countJobs) {
        this.countJobs = countJobs;
    }

    public Integer getCountJobsAllow() {
        return countJobsAllow;
    }

    public void setCountJobsAllow(Integer countJobsAllow) {
        this.countJobsAllow = countJobsAllow;
    }

    public Integer getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(Integer isAllow) {
        this.isAllow = isAllow;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

}