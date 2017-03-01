package com.hp.mss.print.model;

/**
 * Created by hop on 01/03/2017.
 */

public class OrderProduct {
    private int id = 0;
    private int order_id = 0;
    private int product_id = 0;
    private int count = 0;
    private String createAt = "";

    public OrderProduct() {
    }

    public OrderProduct(int order_id, int product_id, int count) {
        this.order_id = order_id;

        this.product_id = product_id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
