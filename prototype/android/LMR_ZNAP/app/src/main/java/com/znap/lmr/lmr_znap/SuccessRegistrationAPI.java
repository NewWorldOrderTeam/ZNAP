package com.znap.lmr.lmr_znap;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuccessRegistrationAPI {
    @SerializedName("__type")
    @Expose
    private String type;
    @SerializedName("CustOrderGuid")
    @Expose
    private String custOrderGuid;
    @SerializedName("CustReceiptLetter")
    @Expose
    private String custReceiptLetter;
    @SerializedName("CustReceiptNum")
    @Expose
    private Integer custReceiptNum;
    @SerializedName("CustJobGuid")
    @Expose
    private String custJobGuid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustOrderGuid() {
        return custOrderGuid;
    }

    public void setCustOrderGuid(String custOrderGuid) {
        this.custOrderGuid = custOrderGuid;
    }

    public String getCustReceiptLetter() {
        return custReceiptLetter;
    }

    public void setCustReceiptLetter(String custReceiptLetter) {
        this.custReceiptLetter = custReceiptLetter;
    }

    public Integer getCustReceiptNum() {
        return custReceiptNum;
    }

    public void setCustReceiptNum(Integer custReceiptNum) {
        this.custReceiptNum = custReceiptNum;
    }

    public String getCustJobGuid() {
        return custJobGuid;
    }

    public void setCustJobGuid(String custJobGuid) {
        this.custJobGuid = custJobGuid;
    }
}