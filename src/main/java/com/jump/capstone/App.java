package com.jump.capstone;


import com.jump.capstone.user.User_input;
import com.jump.capstone.user.user_activity;

import java.sql.SQLException;
import java.time.LocalDate;

import com.jump.capstone.exceptions.userAlreadyExists;
import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.music.music_album;
import com.jump.capstone.security.Password_Handler;
import com.jump.capstone.sql.DAOImpli;
import com.jump.capstone.sql.DAOInter;
import com.jump.capstone.user.Normal_User;

import java.util.List;
import java.util.Optional;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new App();
    }

    public App(){

       
 DAOInter tester= new DAOImpli();

        try {
            tester.makeConnection();
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println("CONNECTION ERROR");
        }

        try {
            
            boolean testRepeat = tester.makeAdminUser("Jerry", "Password123", "Dallas","2Nz8");

            System.out.println(testRepeat);
            

            
            
            
        } catch (userAlreadyExists e) {
            e.printStackTrace();
        }

        
        
        



        //User_input.user_terminal();




    }
}
