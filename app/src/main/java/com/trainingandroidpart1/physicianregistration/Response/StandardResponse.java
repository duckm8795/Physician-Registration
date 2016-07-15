package com.trainingandroidpart1.physicianregistration.Response;

/**
 * Created by kieuduc on 14/07/2016.
 */
public class StandardResponse {
    private  Boolean success;
    private  String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
