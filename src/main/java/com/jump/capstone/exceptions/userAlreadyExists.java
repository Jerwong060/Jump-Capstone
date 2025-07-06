package com.jump.capstone.exceptions;



public class userAlreadyExists extends Exception{
    //Triggers when the Username matches another existing account's username, no repeat usernames
    public userAlreadyExists(String name){
        super("User with Username: "+ name + " already exists, please log into your account");
    }

}
