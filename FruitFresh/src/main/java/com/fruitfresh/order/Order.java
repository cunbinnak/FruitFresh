package com.fruitfresh.order;


import com.fruitfresh.OrderDetail.OrderDetail;
import com.fruitfresh.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderid;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private String email;
    private Date orderdate;
    private Date shipdate;
    private String orderStatus;
    @OneToMany(mappedBy = "orderid")
    private List<OrderDetail> listDetail;

    //thông tin tài khoản mua hàng
    @ManyToOne
    private User  username;


    public Order() {
    }

    public Order(int orderid, String firstname, String lastname, String address, String phone, String email, Date orderdate, Date shipdate, String orderStatus, List<OrderDetail> listDetail, User username) {
        this.orderid = orderid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.orderdate = orderdate;
        this.shipdate = shipdate;
        this.orderStatus = orderStatus;
        this.listDetail = listDetail;
        this.username = username;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Date getShipdate() {
        return shipdate;
    }

    public void setShipdate(Date shipdate) {
        this.shipdate = shipdate;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<OrderDetail> listDetail) {
        this.listDetail = listDetail;
    }
}
