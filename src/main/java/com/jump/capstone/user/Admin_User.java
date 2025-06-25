package com.jump.capstone.user;




import java.util.Optional;

import com.jump.capstone.security.Password_Handler;



public class Admin_User extends Normal_User{

   

    private boolean adminAccess=true;

    private Admin_User(String username, String password){
        super(username,password);
    }

    public Optional<Admin_User> createAdminAccount(String userName, String passWord, String adminCode){
        
    
        if(Password_Handler.password_Checker_Access_Admin(adminCode)){
            Admin_User admin= new Admin_User(userName,passWord);

            return Optional.of(admin);
        }else{
            return Optional.empty();
        }
    
    }
}
