package com.trainingandroidpart1.physicianregistration.Response.SpecialistReponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kieuduc on 10/07/2016.
 */
public class SpecialtyList {
    private Integer specialtyID;
    private String description;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The specialtyID
     */
    public Integer getSpecialtyID() {
        return specialtyID;
    }

    /**
     *
     * @param specialtyID
     * The specialtyID
     */
    public void setSpecialtyID(Integer specialtyID) {
        this.specialtyID = specialtyID;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
