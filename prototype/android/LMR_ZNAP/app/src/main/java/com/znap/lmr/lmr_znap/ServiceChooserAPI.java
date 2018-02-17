package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceChooserAPI {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("GroupId")
    @Expose
    private Integer groupId;
    @SerializedName("IconGroupClass")
    @Expose
    private String iconGroupClass;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("IsOnlyInform")
    @Expose
    private Integer isOnlyInform;
    @SerializedName("OrderWeight")
    @Expose
    private Integer orderWeight;
    @SerializedName("ParameterMask")
    @Expose
    private Integer parameterMask;
    @SerializedName("Priority")
    @Expose
    private Integer priority;
    @SerializedName("RegTypeLogic")
    @Expose
    private Integer regTypeLogic;
    @SerializedName("ServiceCenterGuid")
    @Expose
    private String serviceCenterGuid;
    @SerializedName("ServiceCenterId")
    @Expose
    private Integer serviceCenterId;
    @SerializedName("ServiceGuid")
    @Expose
    private String serviceGuid;
    @SerializedName("ServiceId")
    @Expose
    private Integer serviceId;
    @SerializedName("ServiceLevel")
    @Expose
    private Integer serviceLevel;
    @SerializedName("WindowId")
    @Expose
    private Integer windowId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getIconGroupClass() {
        return iconGroupClass;
    }

    public void setIconGroupClass(String iconGroupClass) {
        this.iconGroupClass = iconGroupClass;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsOnlyInform() {
        return isOnlyInform;
    }

    public void setIsOnlyInform(Integer isOnlyInform) {
        this.isOnlyInform = isOnlyInform;
    }

    public Integer getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(Integer orderWeight) {
        this.orderWeight = orderWeight;
    }

    public Integer getParameterMask() {
        return parameterMask;
    }

    public void setParameterMask(Integer parameterMask) {
        this.parameterMask = parameterMask;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getRegTypeLogic() {
        return regTypeLogic;
    }

    public void setRegTypeLogic(Integer regTypeLogic) {
        this.regTypeLogic = regTypeLogic;
    }

    public String getServiceCenterGuid() {
        return serviceCenterGuid;
    }

    public void setServiceCenterGuid(String serviceCenterGuid) {
        this.serviceCenterGuid = serviceCenterGuid;
    }

    public Integer getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Integer serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

}