package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordToZnapAPI {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("AvgProcTime")
    @Expose
    private Integer avgProcTime;
    @SerializedName("AvgWaitTime")
    @Expose
    private Integer avgWaitTime;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("DeletedJobsCount")
    @Expose
    private Integer deletedJobsCount;
    @SerializedName("ExecutedJobsCount")
    @Expose
    private Integer executedJobsCount;
    @SerializedName("ExtCenterId")
    @Expose
    private Integer extCenterId;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("LocationId")
    @Expose
    private Integer locationId;
    @SerializedName("LocationName")
    @Expose
    private String locationName;
    @SerializedName("ServiceCenterId")
    @Expose
    private Integer serviceCenterId;
    @SerializedName("ServiceCenterName")
    @Expose
    private String serviceCenterName;
    @SerializedName("WaitJobsCount")
    @Expose
    private Integer waitJobsCount;
    @SerializedName("WaitJobsCountOther")
    @Expose
    private Integer waitJobsCountOther;
    @SerializedName("WaitJobsCountSMS")
    @Expose
    private Integer waitJobsCountSMS;
    @SerializedName("WorkplacesInWork")
    @Expose
    private Integer workplacesInWork;
    @SerializedName("WorkplacesInWorkOther")
    @Expose
    private Integer workplacesInWorkOther;
    @SerializedName("WorkplacesInWorkSMS")
    @Expose
    private Integer workplacesInWorkSMS;
    @SerializedName("WorkplacesOutOfWork")
    @Expose
    private Integer workplacesOutOfWork;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAvgProcTime() {
        return avgProcTime;
    }

    public void setAvgProcTime(Integer avgProcTime) {
        this.avgProcTime = avgProcTime;
    }

    public Integer getAvgWaitTime() {
        return avgWaitTime;
    }

    public void setAvgWaitTime(Integer avgWaitTime) {
        this.avgWaitTime = avgWaitTime;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getDeletedJobsCount() {
        return deletedJobsCount;
    }

    public void setDeletedJobsCount(Integer deletedJobsCount) {
        this.deletedJobsCount = deletedJobsCount;
    }

    public Integer getExecutedJobsCount() {
        return executedJobsCount;
    }

    public void setExecutedJobsCount(Integer executedJobsCount) {
        this.executedJobsCount = executedJobsCount;
    }

    public Integer getExtCenterId() {
        return extCenterId;
    }

    public void setExtCenterId(Integer extCenterId) {
        this.extCenterId = extCenterId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Integer serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
    }

    public Integer getWaitJobsCount() {
        return waitJobsCount;
    }

    public void setWaitJobsCount(Integer waitJobsCount) {
        this.waitJobsCount = waitJobsCount;
    }

    public Integer getWaitJobsCountOther() {
        return waitJobsCountOther;
    }

    public void setWaitJobsCountOther(Integer waitJobsCountOther) {
        this.waitJobsCountOther = waitJobsCountOther;
    }

    public Integer getWaitJobsCountSMS() {
        return waitJobsCountSMS;
    }

    public void setWaitJobsCountSMS(Integer waitJobsCountSMS) {
        this.waitJobsCountSMS = waitJobsCountSMS;
    }

    public Integer getWorkplacesInWork() {
        return workplacesInWork;
    }

    public void setWorkplacesInWork(Integer workplacesInWork) {
        this.workplacesInWork = workplacesInWork;
    }

    public Integer getWorkplacesInWorkOther() {
        return workplacesInWorkOther;
    }

    public void setWorkplacesInWorkOther(Integer workplacesInWorkOther) {
        this.workplacesInWorkOther = workplacesInWorkOther;
    }

    public Integer getWorkplacesInWorkSMS() {
        return workplacesInWorkSMS;
    }

    public void setWorkplacesInWorkSMS(Integer workplacesInWorkSMS) {
        this.workplacesInWorkSMS = workplacesInWorkSMS;
    }

    public Integer getWorkplacesOutOfWork() {
        return workplacesOutOfWork;
    }

    public void setWorkplacesOutOfWork(Integer workplacesOutOfWork) {
        this.workplacesOutOfWork = workplacesOutOfWork;
    }

}