package com.example.loginpractice;

public class User {
    public String userName;
    public String userEmail;

    public User(){

    }
    public User(String userName,String userEmail){
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString(){
        return "User{" +
                "userName'" + userName + '\'' +
                ", email ='" + userEmail + '\''+
                '}';
    }
}
