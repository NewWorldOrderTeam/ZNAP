package ua.lviv.iot.lmr_cnap.Pojo;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuccessRegistrationAPI {
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("cnap_id")
    @Expose
    private Object cnapId;
    @SerializedName("service_id")
    @Expose
    private Object serviceId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("hour")
    @Expose
    private String hour;

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getCnapId() {
        return cnapId;
    }

    public void setCnapId(Object cnapId) {
        this.cnapId = cnapId;
    }

    public Object getServiceId() {
        return serviceId;
    }

    public void setServiceId(Object serviceId) {
        this.serviceId = serviceId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

}