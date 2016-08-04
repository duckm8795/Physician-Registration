package com.trainingandroidpart1.physicianregistration.Response.GetOpenDoctorDetailForProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kieuduc on 04/08/2016.
 */
public class Data {

    private List<DocumentList> documentList = new ArrayList<DocumentList>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The documentList
     */
    public List<DocumentList> getDocumentList() {
        return documentList;
    }

    /**
     *
     * @param documentList
     * The documentList
     */
    public void setDocumentList(List<DocumentList> documentList) {
        this.documentList = documentList;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
