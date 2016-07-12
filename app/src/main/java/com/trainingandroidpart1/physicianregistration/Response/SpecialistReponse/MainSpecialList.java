package com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kieuduc on 10/07/2016.
 */
public class MainSpecialList {
    private Boolean success;
    private String message;
    private List<SpecialtyList> specialtyList = new ArrayList<SpecialtyList>();
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
     * The specialtyList
     */
    public List<SpecialtyList> getSpecialtyList() {
        return specialtyList;
    }

    /**
     *
     * @param specialtyList
     * The specialtyList
     */
    public void setSpecialtyList(List<SpecialtyList> specialtyList) {
        this.specialtyList = specialtyList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
