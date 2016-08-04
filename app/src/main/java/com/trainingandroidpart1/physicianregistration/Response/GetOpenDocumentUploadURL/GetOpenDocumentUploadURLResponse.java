package com.trainingandroidpart1.physicianregistration.Response.GetOpenDocumentUploadURL;

/**
 * Created by kieuduc on 04/08/2016.
 */
public class GetOpenDocumentUploadURLResponse {
    String message;
    String imageURL;
    String guid;
    boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
