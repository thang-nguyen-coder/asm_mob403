package com.example.asm.model;

public class SPModel {
    private String _id;
    private String name;
    private float price;
    private String description;

    public SPModel(String _id, String name, float price, String description) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getPid() {
        return _id;
    }

    public void setPid(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
