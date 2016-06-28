package com.trainingandroidpart1.physicianregistration.Response.SetSecurityPin;

/**
 * Created by Admin on 6/28/2016.
 */
public class SetSecurityPinResponse {
//    String  token;
//    String userID;
//    String  securityPin;
    String success;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
