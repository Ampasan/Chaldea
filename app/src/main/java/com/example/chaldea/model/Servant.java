package com.example.chaldea.model;

public class Servant {
    private String name;
    private String description;
    private String servantClass;
    private String photo;

    public Servant(String name, String description, String servantClass, String photo) {
        this.name = name;
        this.description = description;
        this.servantClass = servantClass;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServantClass() {
        return servantClass;
    }

    public void setServantClass(String servantClass) {
        this.servantClass = servantClass;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
