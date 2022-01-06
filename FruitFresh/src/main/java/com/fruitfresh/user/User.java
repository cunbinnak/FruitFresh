package com.fruitfresh.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fruitfresh.order.Order;
import com.fruitfresh.role.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullname;
    private String username;
    private String password;
    private String tokenforgotpass;
    private int countchange;
    private boolean userstatus;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> listRoles = new ArrayList<>();
    @OneToMany(mappedBy ="username")
    @JsonIgnore
    private List<Order> listOrder = new ArrayList<>();

    public User() {
    }


    public User(int id, String fullname, String username, String password, String tokenforgotpass, int countchange, boolean userstatus, List<Role> listRoles, List<Order> listOrder) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.tokenforgotpass = tokenforgotpass;
        this.countchange = countchange;
        this.userstatus = userstatus;
        this.listRoles = listRoles;
        this.listOrder = listOrder;
    }

    public int getCountchange() {
        return countchange;
    }

    public void setCountchange(int countchange) {
        this.countchange = countchange;
    }

    public String getTokenforgotpass() {
        return tokenforgotpass;
    }

    public void setTokenforgotpass(String tokenforgotpass) {
        this.tokenforgotpass = tokenforgotpass;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Order> listOrder) {
        this.listOrder = listOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUserstatus() {
        return userstatus;
    }

    public void setUserstatus(boolean userstatus) {
        this.userstatus = userstatus;
    }



    public List<Role> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Role> listRoles) {
        this.listRoles = listRoles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userstatus=" + userstatus +
                ", listRoles=" + listRoles +
                ", listOrder=" + listOrder +
                '}';
    }
}
