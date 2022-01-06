package com.fruitfresh.OrderDetail;


import com.fruitfresh.Products.Products;

import javax.persistence.*;

@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderdetailid;
    private int orderid;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "productid")
    private Products productid;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(int orderdetailid, int orderid, Products productid, int quantity) {
        this.orderdetailid = orderdetailid;
        this.orderid = orderid;
        this.productid = productid;
        this.quantity = quantity;
    }

    public int getOrderdetailid() {
        return orderdetailid;
    }

    public void setOrderdetailid(int orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public Products getProductid() {
        return productid;
    }

    public void setProductid(Products productid) {
        this.productid = productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
