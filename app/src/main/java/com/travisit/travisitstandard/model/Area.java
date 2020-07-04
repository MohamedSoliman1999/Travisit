package com.travisit.travisitstandard.model;

import java.io.Serializable;

public class Area implements Serializable {
    private Integer id = null;
    private String area = null;

    public Area(Integer id, String area) {
        this.id = id;
        this.area = area;
    }


}
