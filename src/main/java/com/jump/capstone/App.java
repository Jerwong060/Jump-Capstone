package com.jump.capstone;



import java.util.Scanner;

import com.jump.capstone.user.User_input;
;


public class App 
{
    public static void main( String[] args )
    {
        new App();
    }

    public App(){

       Scanner input=new Scanner(System.in);
       User_input.user_terminal(input);
       input.close();

    }
}
