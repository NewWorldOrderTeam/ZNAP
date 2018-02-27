package com.znap.lmr.lmr_znap.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueStateAPI{

    @SerializedName("SrvCenterId")
    @Expose
    private Integer srvCenterId;
    @SerializedName("SrvCenterDescription")
    @Expose
    private String srvCenterDescription;
    @SerializedName("CountOfOperatorsOnline")
    @Expose
    private Integer countOfOperatorsOnline;
    @SerializedName("CountOfRegisteredJobs")
    @Expose
    private Integer countOfRegisteredJobs;
    @SerializedName("CountOfWaitingJobs")
    @Expose
    private Integer countOfWaitingJobs;
    @SerializedName("CountOfExecudedJobs")
    @Expose
    private Integer countOfExecudedJobs;
    @SerializedName("CountOfDroppedJobs")
    @Expose
    private Integer countOfDroppedJobs;
    @SerializedName("AverageWaitingTime")
    @Expose
    private Integer averageWaitingTime;
    @SerializedName("MaximumWaitingTime")
    @Expose
    private Integer maximumWaitingTime;
    @SerializedName("AverageExecutingTime")
    @Expose
    private Integer averageExecutingTime;
    @SerializedName("MaximumExecutingTime")
    @Expose
    private Integer maximumExecutingTime;
    @SerializedName("HourOfDay")
    @Expose
    private Integer hourOfDay;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Color")
    @Expose
    private String color;
    @SerializedName("CountOfServingAndWaitingJobs_Serving")
    @Expose
    private Integer countOfServingAndWaitingJobsServing;
    @SerializedName("CountOfServingAndWaitingJobs_Waiting")
    @Expose
    private Integer countOfServingAndWaitingJobsWaiting;

    public Integer getSrvCenterId() {
        return srvCenterId;
    }

    public void setSrvCenterId(Integer srvCenterId) {
        this.srvCenterId = srvCenterId;
    }

    public String getSrvCenterDescription() {
        return srvCenterDescription;
    }

    public void setSrvCenterDescription(String srvCenterDescription) {
        this.srvCenterDescription = srvCenterDescription;
    }

    public Integer getCountOfOperatorsOnline() {
        return countOfOperatorsOnline;
    }

    public void setCountOfOperatorsOnline(Integer countOfOperatorsOnline) {
        this.countOfOperatorsOnline = countOfOperatorsOnline;
    }

    public Integer getCountOfRegisteredJobs() {
        return countOfRegisteredJobs;
    }

    public void setCountOfRegisteredJobs(Integer countOfRegisteredJobs) {
        this.countOfRegisteredJobs = countOfRegisteredJobs;
    }

    public Integer getCountOfWaitingJobs() {
        return countOfWaitingJobs;
    }

    public void setCountOfWaitingJobs(Integer countOfWaitingJobs) {
        this.countOfWaitingJobs = countOfWaitingJobs;
    }

    public Integer getCountOfExecudedJobs() {
        return countOfExecudedJobs;
    }

    public void setCountOfExecudedJobs(Integer countOfExecudedJobs) {
        this.countOfExecudedJobs = countOfExecudedJobs;
    }

    public Integer getCountOfDroppedJobs() {
        return countOfDroppedJobs;
    }

    public void setCountOfDroppedJobs(Integer countOfDroppedJobs) {
        this.countOfDroppedJobs = countOfDroppedJobs;
    }

    public Integer getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime(Integer averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public Integer getMaximumWaitingTime() {
        return maximumWaitingTime;
    }

    public void setMaximumWaitingTime(Integer maximumWaitingTime) {
        this.maximumWaitingTime = maximumWaitingTime;
    }

    public Integer getAverageExecutingTime() {
        return averageExecutingTime;
    }

    public void setAverageExecutingTime(Integer averageExecutingTime) {
        this.averageExecutingTime = averageExecutingTime;
    }

    public Integer getMaximumExecutingTime() {
        return maximumExecutingTime;
    }

    public void setMaximumExecutingTime(Integer maximumExecutingTime) {
        this.maximumExecutingTime = maximumExecutingTime;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(Integer hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCountOfServingAndWaitingJobsServing() {
        return countOfServingAndWaitingJobsServing;
    }

    public void setCountOfServingAndWaitingJobsServing(Integer countOfServingAndWaitingJobsServing) {
        this.countOfServingAndWaitingJobsServing = countOfServingAndWaitingJobsServing;
    }

    public Integer getCountOfServingAndWaitingJobsWaiting() {
        return countOfServingAndWaitingJobsWaiting;
    }

    public void setCountOfServingAndWaitingJobsWaiting(Integer countOfServingAndWaitingJobsWaiting) {
        this.countOfServingAndWaitingJobsWaiting = countOfServingAndWaitingJobsWaiting;
    }

}