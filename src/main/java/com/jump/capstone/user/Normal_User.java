package com.jump.capstone.user;

import java.time.LocalDate;
import com.jump.capstone.security.Password_Handler;


public class Normal_User{
    private String user_name;
    private String password;
    private LocalDate date_created;
    private boolean admin;
    private int id=0;
    private String securityAnswer;
    
    public Normal_User(String user_name, String password,String securityAns,boolean adminAccess){
        this.user_name=user_name;
        this.id=id++;
        this.date_created= LocalDate.now();
        this.password=Password_Handler.password_worker_access(password);
        this.securityAnswer=Password_Handler.password_worker_access(securityAns);
        this.admin= adminAccess;
    }

    public Normal_User(String user_name, String password,String securityAns,LocalDate dateMade,int id,boolean admin_access){
        this.user_name=user_name;
        this.id=id;
        this.date_created= dateMade;
        this.password=password;
        this.securityAnswer=securityAns;
        this.admin= admin_access;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDate_created() {
        return date_created;
    }

    public int getId() {
        return id;
    }
    public boolean isAdminAccess() {
        return admin;
    }
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public String toString() {
        return "Normal_User [user_name=" + user_name + ", password=" + password + ", date_created=" + date_created
                + ", admin=" + admin + ", id=" + id + ", securityAnswer=" + securityAnswer + "]";
    }

    
}
