package com.jump.capstone.exceptions;

public class userExceedPasswordAttempts extends Exception{
    //user attempted to login too many times
    public userExceedPasswordAttempts(){
        super("USED ALL PASSWORD ENTRY ATTEMPTS TRY AGAIN LATER");
    }
    

}
