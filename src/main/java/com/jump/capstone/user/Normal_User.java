package com.jump.capstone.user;

import java.time.LocalDate;
import com.jump.capstone.security.Password_Handler;


public class Normal_User{
    private String user_name;
    private String password;
    private LocalDate date_created;
    private int id=0;
    private boolean adminAccess = false; 
    private String securityAnswer;
    
    public Normal_User(String user_name, String password,String securityAns){
        this.user_name=user_name;
        this.id=id++;
        this.date_created= LocalDate.now();
        this.password=Password_Handler.password_worker_access(password);
        this.securityAnswer=Password_Handler.password_worker_access(securityAns);
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
        return adminAccess;
    }
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
}
