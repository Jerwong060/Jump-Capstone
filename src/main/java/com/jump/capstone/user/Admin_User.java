package com.jump.capstone.user;




import java.util.Optional;

import com.jump.capstone.security.Password_Handler;



public class Admin_User extends Normal_User{

   

    private boolean adminAccess=true;

    private Admin_User(String username, String password,String securityAns){
        super(username,password,securityAns);
    }

    public Optional<Admin_User> createAdminAccount(String userName, String passWord, String adminCode,String securityAns){
        
    
        if(Password_Handler.password_Checker_Access_Admin(adminCode)){
            Admin_User admin= new Admin_User(userName,passWord, securityAns);

            return Optional.of(admin);
        }else{
            return Optional.empty();
        }
    
    }
    
    @Override
    public boolean isAdminAccess() {
        return adminAccess;
    }

  
    

}
