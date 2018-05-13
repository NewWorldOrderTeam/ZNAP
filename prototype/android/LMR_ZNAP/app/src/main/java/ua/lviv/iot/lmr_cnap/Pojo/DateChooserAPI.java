package ua.lviv.iot.lmr_cnap.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DateChooserAPI {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("times")
    @Expose
    private List<HoursChooserAPI> times = null;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<HoursChooserAPI> getTimes() {
        return times;
    }

    public void setTimes(List<HoursChooserAPI> times) {
        this.times = times;
    }
}