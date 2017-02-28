package com.hp.mss.print.model;

/**
 * Created by hop on 27/02/2017.
 */

public class Product {

    public Product(String name, int unitId) {
        this.name = name;
        this.unitId = unitId;
    }

    int id;
    String name;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    int unitId;
    String createAt;

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
