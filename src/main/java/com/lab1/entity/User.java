package com.lab1.entity;

import org.hibernate.annotations.Entity;
import javax.persistence.Column;
import javax.persistence.Id;

@Entity

public class User {

    @Id
    @Column(name = "EMAIL")
    String email;

    @Column(name = "PHONE NUMBER")
    String phone;

    @Column(name = "PASSWORD")
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
