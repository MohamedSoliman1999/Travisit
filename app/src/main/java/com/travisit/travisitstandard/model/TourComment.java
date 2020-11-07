package com.travisit.travisitstandard.model;

public class TourComment {

    /**
     * id : 1
     * comment : commmmmment test
     * TourId : 2
     * createdAt : 2020-10-23T16:34:45.000Z
     * updatedAt : 2020-10-23T16:34:45.000Z
     */

    private int id;
    private String comment;
    private int TourId;
    private String createdAt;
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTourId() {
        return TourId;
    }

    public void setTourId(int TourId) {
        this.TourId = TourId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
