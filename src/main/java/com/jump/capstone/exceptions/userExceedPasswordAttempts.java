package com.jump.capstone.exceptions;

public class userExceedPasswordAttempts extends Exception{

    public userExceedPasswordAttempts(){
        super("USED ALL PASSWORD ENTRY ATTEMPTS TRY AGAIN LATER");
    }
    

}
