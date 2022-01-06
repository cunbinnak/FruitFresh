package com.fruitfresh.categories;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fruitfresh.Products.Products;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cate_id;
    private String cate_name;
    private String creator;
    private Date createDate;
    private boolean cate_status;
    @JsonBackReference
    @OneToMany(mappedBy = "categories")
    private List<Products> productsList;

    public Categories() {
    }

    public Categories(int cate_id, String cate_name, String creator, Date createDate, boolean cate_status, List<Products> productsList) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.creator = creator;
        this.createDate = createDate;
        this.cate_status = cate_status;
        this.productsList = productsList;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isCate_status() {
        return cate_status;
    }

    public void setCate_status(boolean cate_status) {
        this.cate_status = cate_status;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
