package com.hp.mss.print.model;

/**
 * Created by hop on 28/02/2017.
 */

public class Unit {
    int id;
    String name;

    public Unit(int id, String name) {
        this.id = id;
        this.name = name;
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
}