package com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 6/30/2016.
 */
public class Main {
    private Boolean success;
    private String message;
    private List<VerificationDocType> verificationDocTypes = new ArrayList<VerificationDocType>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The verificationDocTypes
     */
    public List<VerificationDocType> getVerificationDocTypes() {
        return verificationDocTypes;
    }

    /**
     *
     * @param verificationDocTypes
     * The verificationDocTypes
     */
    public void setVerificationDocTypes(List<VerificationDocType> verificationDocTypes) {
        this.verificationDocTypes = verificationDocTypes;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
