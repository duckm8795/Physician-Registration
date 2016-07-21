package com.trainingandroidpart1.physicianregistration.Entity;

import me.yokeyword.indexablelistview.IndexEntity;

/**
 * Created by kieuduc on 10/07/2016.
 */
public class SpecialistEntity extends IndexEntity {
    private String name;
    private int id;

    public SpecialistEntity(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
