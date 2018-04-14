package com.znap.lmr.lmr_znap.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueStateAPI {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
