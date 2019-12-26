package com.projectHbase.javaHbase.bean;

public class User {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String avatar;
    private String job;
    private String gender;

    public User() {
    }

    
    public User(String id,String email, String password, String name, String phone, String avatar, String job, String gender) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
        this.job = job;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", phone=" + phone + ", avatar=" + avatar + ", job=" + job + ", gender=" + gender + '}';
    }
	
    
	
}
