package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueStateAPI {

    @SerializedName("SrvCenterId")
    @Expose
    private Integer srvCenterId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CustomerCount")
    @Expose
    private Integer customerCount;
    @SerializedName("JobCount")
    @Expose
    private Integer jobCount;
    @SerializedName("TotalWp")
    @Expose
    private Integer totalWp;
    @SerializedName("InWorkWp")
    @Expose
    private Integer inWorkWp;
    @SerializedName("OutOfWorkWp")
    @Expose
    private Integer outOfWorkWp;

    public Integer getSrvCenterId() {
        return srvCenterId;
    }

    public void setSrvCenterId(Integer srvCenterId) {
        this.srvCenterId = srvCenterId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public Integer getJobCount() {
        return jobCount;
    }

    public void setJobCount(Integer jobCount) {
        this.jobCount = jobCount;
    }

    public Integer getTotalWp() {
        return totalWp;
    }

    public void setTotalWp(Integer totalWp) {
        this.totalWp = totalWp;
    }

    public Integer getInWorkWp() {
        return inWorkWp;
    }

    public void setInWorkWp(Integer inWorkWp) {
        this.inWorkWp = inWorkWp;
    }

    public Integer getOutOfWorkWp() {
        return outOfWorkWp;
    }

    public void setOutOfWorkWp(Integer outOfWorkWp) {
        this.outOfWorkWp = outOfWorkWp;
    }

}