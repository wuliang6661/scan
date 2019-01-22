package com.wul.scan.data;

public class UserBO {


    /**
     * factoryCode : 001
     * userId : 6
     * factoryId : 1
     * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzenMiLCJjcmVhdGVkIjoxNTQ4MTI1OTgzMzQ0LCJleHAiOjE1NDgyMTIzODN9.wgEd0lQ-oDFiY-DBoQhITCGg_Z1Y7REdu--pWFtVLQmWc3g-dEASw8G1vw4P7WLUqD47F5IDZBmo1-4zBNIplQ
     */

    private String factoryCode;
    private int userId;
    private int factoryId;
    private String token;

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
