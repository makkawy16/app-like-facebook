package com.example.facebook.data.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String UserID,name, email, password, phone, imgUrl, gender ,age;


    public UserModel(String userID, String name, String email, String password, String phone, String imgUrl, String gender, String age) {
        UserID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.gender = gender;
        this.age = age;
    }

    public UserModel() {
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
