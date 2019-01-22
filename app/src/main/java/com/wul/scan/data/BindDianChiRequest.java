package com.wul.scan.data;

public class BindDianChiRequest {


    /**
     * batteryLabel : string
     * imei : string
     * orderId : 0
     * processLabelId : 0
     */

    private String batteryLabel;
    private String imei;
    private int orderId;
    private String processLabelId;
    public String token;

    public String getBatteryLabel() {
        return batteryLabel;
    }

    public void setBatteryLabel(String batteryLabel) {
        this.batteryLabel = batteryLabel;
    }

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

    public String getProcessLabelId() {
        return processLabelId;
    }

    public void setProcessLabelId(String processLabelId) {
        this.processLabelId = processLabelId;
    }
}
