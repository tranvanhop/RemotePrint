package com.hp.mss.print.model;

import java.util.ArrayList;

/**
 * Created by hop on 01/03/2017.
 */

public class Order {
    private int id = 0;
    private String name = "";
    private String createAt = "";
    private ArrayList<Product> products = new ArrayList<Product>();

    public Order() {
    }

    public Order(String name) {

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
