package com.fruitfresh.Products;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fruitfresh.categories.Categories;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productid;
    private String productname;
    private Float price;
    private String description;
    private Date createDate;
    private String creator;
    private String urlImg;
    private boolean productStatus;
    @ManyToOne
    private Categories categories;

    public Products() {
    }

    public Products(int productid, String productname, Float price, String description, Date createDate, String creator, String urlImg, boolean productStatus, Categories categories) {
        this.productid = productid;
        this.productname = productname;
        this.price = price;
        this.description = description;
        this.createDate = createDate;
        this.creator = creator;
        this.urlImg = urlImg;
        this.productStatus = productStatus;
        this.categories = categories;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProduct_name(String productname) {
        this.productname = productname;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }


}
