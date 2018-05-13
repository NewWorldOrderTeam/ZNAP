package ua.lviv.iot.lmr_cnap.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HoursChooserAPI {

    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("stop")
    @Expose
    private String stop;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

}