package com.trainingandroidpart1.physicianregistration.Response.GetDegreeList;

/**
 * Created by kieum on 7/7/2016.
 */
public class MedDegreeList {

    private Integer medDegreeID;
    private String name;


    /**
     *
     * @return
     * The medDegreeID
     */
    public Integer getMedDegreeID() {
        return medDegreeID;
    }

    /**
     *
     * @param medDegreeID
     * The medDegreeID
     */
    public void setMedDegreeID(Integer medDegreeID) {
        this.medDegreeID = medDegreeID;
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


}
