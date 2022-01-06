package com.fruitfresh.DTO;


import com.fruitfresh.categories.Categories;

import java.util.Date;
import java.util.List;

public class ProductDTO {

    private int product_id;
    private String product_name;
    private Float price;
    private String description;
    private Date createDate;
    private String creator;
    private String urlImg;
    private List<Categories> listCate;

    public ProductDTO() {
    }

    public ProductDTO(int product_id, String product_name, Float price, String description, Date createDate, String creator, String urlImg, List<Categories> listCate) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.createDate = createDate;
        this.creator = creator;
        this.urlImg = urlImg;
        this.listCate = listCate;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public List<Categories> getListCate() {
        return listCate;
    }

    public void setListCate(List<Categories> listCate) {
        this.listCate = listCate;
    }
}
