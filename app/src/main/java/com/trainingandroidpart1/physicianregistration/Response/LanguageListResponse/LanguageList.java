package com.trainingandroidpart1.physicianregistration.Response.LanguageListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kieum on 7/12/2016.
 */
public class LanguageList {
    private String languageCode;
    private String name;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     *
     * @return
     * The languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     *
     * @param languageCode
     * The languageCode
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
