package com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kieum on 7/12/2016.
 */
public class MainLanguageListResponse {
    private Boolean success;
    private String message;
    private List<LanguageList> languageList = new ArrayList<>();
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
     * The languageList
     */
    public List<LanguageList> getLanguageList() {
        return languageList;
    }

    /**
     *
     * @param languageList
     * The languageList
     */
    public void setLanguageList(List<LanguageList> languageList) {
        this.languageList = languageList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
