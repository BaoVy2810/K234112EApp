package com.example.models;

import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable {
    private String customerid;
    private String customername;
    private String phone;
    private String email;
    private String address;
    private Date birthDay;
    public Customer() {
    }
    public Customer(String customerid, String customername, String phone, String email, String address, Date birthDay) {
        this.customerid = customerid;
        this.customername = customername;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthDay = birthDay;
    }

    public String getCustomerId() {
        return customerid;
    }

    public void setCustomerId(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerid='" + customerid + '\'' +
                ", customername='" + customername + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
