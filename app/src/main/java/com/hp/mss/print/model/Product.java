package com.hp.mss.print.model;

/**
 * Created by hop on 27/02/2017.
 */

public class Product {

    public Product(String name, float price, int unitId, String tag, String thumbnail, String image) {
        this.name = name;
        this.price = price;
        this.unitId = unitId;
        this.tag = tag;
        this.thumbnail = thumbnail;
        this.image = image;
    }

    private int id;
    private String name;
    private float price;
    private int unitId;
    private String tag;
    private String thumbnail;
    private String image;
    private String createAt;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
