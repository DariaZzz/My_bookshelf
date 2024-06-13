package com.example.server_test.model;

import java.util.Objects;

public class User {

    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String date_of_birth;
    private String w_m;
    private String books;



    /*public User(Integer id, String username, String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }*/


    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setW_m(String w_m) {
        this.w_m = w_m;
    }

    public String getW_m() {
        return w_m;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getBooks() {
        return books;
    }
}

