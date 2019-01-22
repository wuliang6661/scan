package com.wul.scan.data;

public class BindGuochengRequest {


    /**
     * imei : string
     * orderId : 0
     * processLabel : string
     */

    private String imei;
    private int orderId;
    private String processLabel;
    public String token;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProcessLabel() {
        return processLabel;
    }

    public void setProcessLabel(String processLabel) {
        this.processLabel = processLabel;
    }
}
