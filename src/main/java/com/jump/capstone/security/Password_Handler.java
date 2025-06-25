package com.jump.capstone.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jump.capstone.connection.ConnectionManager;



public class Password_Handler {

    
    private static Connection connection = null;

	
	public static void establishConnection() throws ClassNotFoundException, SQLException {
		
		if(connection == null) {
			connection = ConnectionManager.getConnection();
		}
	}
	

	public static void closeConnection() throws SQLException {
		connection.close();
	}
	


    public static String password_worker_access(String plaintext){
       return password_worker(plaintext);
    }

    public static boolean password_Checker_Access_Admin(String plaintext){
        return password_checker_Admin(plaintext);
    }

    public static Boolean password_checker_access(String plaintext,int user_id){
       return password_checker(plaintext,user_id);
    }



    private static String password_worker(String plaintext){
        String hashed = BCrypt.withDefaults().hashToString(12, plaintext.toCharArray());
        
        return hashed;
    }




    private static boolean password_checker_Admin(String plaintext){
        String hashedResult="$2a$12$Dq08YVJyKozOCkNrDTaNzu7xTJPFdCKbX8nlwRstVZHw3KysVWo2q";
        
        BCrypt.Result checkPassword = BCrypt.verifyer().verify(plaintext.toCharArray(),hashedResult);

        return checkPassword.verified;
    }


    private static boolean password_checker(String plaintext,int user_id){
        String hashedResult=" ";
        try {

            connection=ConnectionManager.getConnection();

            PreparedStatement getPasswordPreparedStatement = connection.prepareStatement("Select user_pass from user where user_id = ?");

            getPasswordPreparedStatement.setInt(1, user_id);

            ResultSet results= getPasswordPreparedStatement.executeQuery();

            if(results.next()){
                hashedResult= results.getString(1);
            }else{
                return false;
            }
            

            closeConnection();
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }


        BCrypt.Result checkPassword = BCrypt.verifyer().verify(plaintext.toCharArray(),hashedResult);

        return checkPassword.verified;
    }

}
