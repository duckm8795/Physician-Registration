package com.trainingandroidpart1.physicianregistration.Response.GetOpenDoctorDetailForProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kieuduc on 04/08/2016.
 */
public class DocumentList {
    private Integer documentId;
    private String documentTitle;
    private String imageURL;
    private String imageURLThumbnail;
    private String status;
    private Integer rejectReasonId;
    private Object rejectReasonDescription;
    private Integer sortOrder;
    private Integer docTypeId;
    private Integer countryDocTypeId;
    private String guid;
    private Integer ecpid;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The documentId
     */
    public Integer getDocumentId() {
        return documentId;
    }

    /**
     *
     * @param documentId
     * The documentId
     */
    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    /**
     *
     * @return
     * The documentTitle
     */
    public String getDocumentTitle() {
        return documentTitle;
    }

    /**
     *
     * @param documentTitle
     * The documentTitle
     */
    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    /**
     *
     * @return
     * The imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     *
     * @param imageURL
     * The imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     *
     * @return
     * The imageURLThumbnail
     */
    public String getImageURLThumbnail() {
        return imageURLThumbnail;
    }

    /**
     *
     * @param imageURLThumbnail
     * The imageURLThumbnail
     */
    public void setImageURLThumbnail(String imageURLThumbnail) {
        this.imageURLThumbnail = imageURLThumbnail;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The rejectReasonId
     */
    public Integer getRejectReasonId() {
        return rejectReasonId;
    }

    /**
     *
     * @param rejectReasonId
     * The rejectReasonId
     */
    public void setRejectReasonId(Integer rejectReasonId) {
        this.rejectReasonId = rejectReasonId;
    }

    /**
     *
     * @return
     * The rejectReasonDescription
     */
    public Object getRejectReasonDescription() {
        return rejectReasonDescription;
    }

    /**
     *
     * @param rejectReasonDescription
     * The rejectReasonDescription
     */
    public void setRejectReasonDescription(Object rejectReasonDescription) {
        this.rejectReasonDescription = rejectReasonDescription;
    }

    /**
     *
     * @return
     * The sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     *
     * @param sortOrder
     * The sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

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
     * The guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     *
     * @param guid
     * The guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     *
     * @return
     * The ecpid
     */
    public Integer getEcpid() {
        return ecpid;
    }

    /**
     *
     * @param ecpid
     * The ecpid
     */
    public void setEcpid(Integer ecpid) {
        this.ecpid = ecpid;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
