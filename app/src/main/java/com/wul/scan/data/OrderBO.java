package com.wul.scan.data;

public class OrderBO {


    /**
     * productCode : 3010200014
     * orderId : 1
     * orderNum : 51A2-1810110003
     * productNum : 15
     * productName : 锂离子电池
     */

    private String productCode;
    private int orderId;
    private String orderNum;
    private int productNum;
    private String productName;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
