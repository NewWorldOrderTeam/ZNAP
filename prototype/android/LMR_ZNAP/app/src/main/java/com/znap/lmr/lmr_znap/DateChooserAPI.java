package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateChooserAPI {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("CountJobs")
    @Expose
    private Integer countJobs;
    @SerializedName("CountJobsAllow")
    @Expose
    private Integer countJobsAllow;
    @SerializedName("DatePart")
    @Expose
    private String datePart;
    @SerializedName("Exclude")
    @Expose
    private Integer exclude;
    @SerializedName("IsAllow")
    @Expose
    private Integer isAllow;
    @SerializedName("ScheduleBreak")
    @Expose
    private Integer scheduleBreak;
    @SerializedName("StartBreak")
    @Expose
    private String startBreak;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("StopBreak")
    @Expose
    private String stopBreak;
    @SerializedName("StopTime")
    @Expose
    private String stopTime;

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

    public String getDatePart() {
        return datePart;
    }

    public void setDatePart(String datePart) {
        this.datePart = datePart;
    }

    public Integer getExclude() {
        return exclude;
    }

    public void setExclude(Integer exclude) {
        this.exclude = exclude;
    }

    public Integer getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(Integer isAllow) {
        this.isAllow = isAllow;
    }

    public Integer getScheduleBreak() {
        return scheduleBreak;
    }

    public void setScheduleBreak(Integer scheduleBreak) {
        this.scheduleBreak = scheduleBreak;
    }

    public String getStartBreak() {
        return startBreak;
    }

    public void setStartBreak(String startBreak) {
        this.startBreak = startBreak;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopBreak() {
        return stopBreak;
    }

    public void setStopBreak(String stopBreak) {
        this.stopBreak = stopBreak;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

}