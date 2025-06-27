package com.jump.capstone.exceptions;

import com.jump.capstone.user.Normal_User;

public class userAlreadyExists extends Exception{

    public userAlreadyExists(Normal_User user){
        super("User with Username: "+ user.getUser_name() + "already exists, please log into your account");
    }

}
