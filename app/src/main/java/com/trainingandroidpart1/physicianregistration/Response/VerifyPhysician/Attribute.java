package com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 6/30/2016.
 */
public class Attribute {
    private Integer attributeID;
    private String value;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The attributeID
     */
    public Integer getAttributeID() {
        return attributeID;
    }

    /**
     *
     * @param attributeID
     * The attributeID
     */
    public void setAttributeID(Integer attributeID) {
        this.attributeID = attributeID;
    }

    /**
     *
     * @return
     * The value
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
