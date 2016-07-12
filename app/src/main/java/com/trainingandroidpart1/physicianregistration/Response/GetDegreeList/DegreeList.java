package com.trainingandroidpart1.physicianregistration.Response.GetDegreeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kieum on 7/7/2016.
 */
public class DegreeList {
    private Boolean success;
    private String message;
    private List<MedDegreeList> medDegreeList = new ArrayList<>();

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
     * The medDegreeList
     */
    public List<MedDegreeList> getMedDegreeList() {
        return medDegreeList;
    }

    /**
     *
     * @param medDegreeList
     * The medDegreeList
     */
    public void setMedDegreeList(List<MedDegreeList> medDegreeList) {
        this.medDegreeList = medDegreeList;
    }


}
