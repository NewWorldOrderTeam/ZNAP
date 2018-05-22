package ua.lviv.iot.lmr_cnap.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class ActivatedAccountRespone {

    @SerializedName("non_field_errors")
    @Expose
    private List<String> nonFieldErrors = null;

    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }

    public void setNonFieldErrors(List<String> nonFieldErrors) {
        this.nonFieldErrors = nonFieldErrors;
    }

}