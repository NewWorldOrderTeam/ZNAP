package com.znap.lmr.lmr_znap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeOfServiceAPI {

    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("ActionTypeId")
    @Expose
    private Integer actionTypeId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("GroupGuid")
    @Expose
    private String groupGuid;
    @SerializedName("GroupId")
    @Expose
    private Integer groupId;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("OrderWeight")
    @Expose
    private Integer orderWeight;
    @SerializedName("ParentGourpId")
    @Expose
    private Integer parentGourpId;
    @SerializedName("ServiceCenterGuid")
    @Expose
    private String serviceCenterGuid;
    @SerializedName("ServiceCenterId")
    @Expose
    private Integer serviceCenterId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(Integer orderWeight) {
        this.orderWeight = orderWeight;
    }

    public Integer getParentGourpId() {
        return parentGourpId;
    }

    public void setParentGourpId(Integer parentGourpId) {
        this.parentGourpId = parentGourpId;
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

}