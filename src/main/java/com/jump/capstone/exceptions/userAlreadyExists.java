package com.jump.capstone.exceptions;



public class userAlreadyExists extends Exception{

    public userAlreadyExists(String name){
        super("User with Username: "+ name + " already exists, please log into your account");
    }

}
