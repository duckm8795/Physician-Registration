package com.trainingandroidpart1.physicianregistration.Response.GetOpenDoctorDetailForProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kieuduc on 04/08/2016.
 */
public class MainResponse {

    private Boolean success;
    private String message;
    private Data data;
    private Object errorCode;
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     *
     * @return
     * The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The errorCode
     */
    public Object getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     * The errorCode
     */
    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
