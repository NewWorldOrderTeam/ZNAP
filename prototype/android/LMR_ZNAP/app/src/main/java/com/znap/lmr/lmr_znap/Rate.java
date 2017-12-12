package com.znap.lmr.lmr_znap;

/**
 * Created by Zava on 11.12.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rate {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("znap_id")
    @Expose
    private Integer znapId;
    @SerializedName("admin_id")
    @Expose
    private Object adminId;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getZnapId() {
        return znapId;
    }

    public void setZnapId(Integer znapId) {
        this.znapId = znapId;
    }

    public Object getAdminId() {
        return adminId;
    }

    public void setAdminId(Object adminId) {
        this.adminId = adminId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

}