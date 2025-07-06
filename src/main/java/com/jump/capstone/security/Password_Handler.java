package com.jump.capstone.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jump.capstone.connection.ConnectionManager;



public class Password_Handler {

    
    private static Connection connection = null;

	

	

    //public accesser for generating a hashed representation of a plain text string
    public static String password_worker_access(String plaintext){
       return password_worker(plaintext);
    }

    //public accesser for admin code checker when a new admin account is being created 
    public static boolean password_Checker_Access_Admin(String plaintext){
        return password_checker_Admin(plaintext);
    }

    //public accesser for when a user is logging in to check password
    public static Boolean password_checker_access(String plaintext,String user_name){
       return password_checker_pass(plaintext,user_name);
    }

    //public accesser for when a user wants to change password without going through login, checks security question
    public static boolean password_checker_access_ans(String query, String ansDatabase){
        return password_checker_ans(query, ansDatabase);
    }

    //hashes plaintext passwords
    private static String password_worker(String plaintext){
        String hashed = BCrypt.withDefaults().hashToString(12, plaintext.toCharArray());
        
        return hashed;
    }



    //checks plaintext admin code whenever a new admin account wants to be made
    private static boolean password_checker_Admin(String plaintext){
        String hashedResult="$2a$12$bpZyovdmMSR1Y7.dWk.dj.THvuQdfjdvDFnQC3eZzOSMZIYmg85Ni";
        
        BCrypt.Result checkPassword = BCrypt.verifyer().verify(plaintext.toCharArray(),hashedResult);

        return checkPassword.verified;
    }

    // checks passwords at log in by taking a username, getting back the hashed password, and compares the two. 
    private static boolean password_checker_pass(String plaintext,String user_name){
        String hashedResult=" ";
        try {

            connection=ConnectionManager.getConnection();

            PreparedStatement getPasswordPreparedStatement = connection.prepareStatement("Select user_pass from user where user_name= ?");

            getPasswordPreparedStatement.setString(1, user_name);

            ResultSet results= getPasswordPreparedStatement.executeQuery();

            if(results.next()){
                hashedResult= results.getString(1);
            }else{
                return false;
            }
            

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }


        BCrypt.Result checkPassword = BCrypt.verifyer().verify(plaintext.toCharArray(),hashedResult);

        return checkPassword.verified;
    }

    //checks security question answer whenever a password reset occurs without a login. 
    private static boolean password_checker_ans(String plaintext,String answer){

        BCrypt.Result checkPassword = BCrypt.verifyer().verify(plaintext.toCharArray(),answer);

        return checkPassword.verified;
    }


}
