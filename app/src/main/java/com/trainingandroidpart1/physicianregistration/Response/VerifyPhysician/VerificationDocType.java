package com.trainingandroidpart1.physicianregistration.Response.VerifyPhysician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 6/30/2016.
 */
public class VerificationDocType {
    private Integer docTypeId;
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private Integer countryDocTypeId;
    private String title;
    private String briefDescription;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The docTypeId
     */
    public Integer getDocTypeId() {
        return docTypeId;
    }

    /**
     *
     * @param docTypeId
     * The docTypeId
     */
    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    /**
     *
     * @return
     * The attributes
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     *
     * @param attributes
     * The attributes
     */
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /**
     *
     * @return
     * The countryDocTypeId
     */
    public Integer getCountryDocTypeId() {
        return countryDocTypeId;
    }

    /**
     *
     * @param countryDocTypeId
     * The countryDocTypeId
     */
    public void setCountryDocTypeId(Integer countryDocTypeId) {
        this.countryDocTypeId = countryDocTypeId;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The briefDescription
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     *
     * @param briefDescription
     * The briefDescription
     */
    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
