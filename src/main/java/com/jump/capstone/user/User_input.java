package com.jump.capstone.user;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.sql.*;

public class User_input {

    private static Scanner input=new Scanner(System.in);
    private static Optional<Normal_User> user=Optional.empty();
    private static DAOInter trackerDAO= new DAOImpli();

    public static void user_terminal(){
        
    
        boolean stop=false;

        System.out.println("\nWelcome to Music Track! A WONG Product");

        while(!stop){

            stop = bootupPage();
            

        }

        input.close();

    }









    private static boolean bootupPage(){

        int choice=-1;
        int attempts=0;
        boolean stop=false;
        while(!stop){

            System.out.println("1-Login");
            System.out.println("2-Make Account");
            System.out.println("3-Exit Program");

            try {
                choice=input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please Enter either 1,2, or 3\n");
                input.nextLine();
            }
            
            try {
                switch (choice) {
                case 1:
                    user=userLogin();
                    if(user.isPresent()){
                        System.out.println("Logged in as " + user.get().getUser_name()+ "\n");
                    }else{
                        System.out.println("login Failed");
                        attempts++;
                    }
                    if(attempts>5){
                        throw new userExceedPasswordAttempts();
                    }
                    break;
                case 2:
                    input.nextLine();
                    System.out.println("Account Type");
                    String user_type=input.nextLine();

                    boolean createResult= userCreate(user_type);

                    if(!createResult){
                        System.out.println("FAILED TO CREATE ACCOUNT\n");
                    }
                    break;
                case 3:
                    stop=true;
                    break;
                default:
                    break;
            }
            } catch (userExceedPasswordAttempts e) {
                System.out.println(e.getMessage());
                return true;
            }

            if(user.isPresent()){
                mainPage(user);
            }
            

    }
        return true;
}




    private static void mainPage(Optional<Normal_User> user){
        int choice=-1;
        boolean stop=false;

        while(!stop){
            System.out.println("What Would You Like To Do?");
            System.out.println("0-Log Out");
            System.out.println("1-Tracker Info");
            System.out.println("2-Track A New Song");
            System.out.println("3-Edit Tracker Infomation");
            System.out.println("4-Look at Albums");
            System.out.println("5-Edit Account Info");
            

            if(user.get().isAdminAccess()){
                System.out.println("6-Look at Site Statistics");
                System.out.println("7- Add Album");
            }
            
            try{
                choice= input.nextInt();
            }catch(InputMismatchException e){
                System.out.println("NUMBERS ONLY");
                input.nextLine();
            }

            switch (choice) {
                case 0:
                    stop=true;
                    user=Optional.empty();
                    bootupPage();
                    break;
                case 1:
                    System.out.println("Select The Type of Search: ALL, By ID, By Status");
                    String choice_search= input.nextLine();

                    tracker_lookup(choice_search);

                    break;
                case 2:

                    create_newTracker();
                    break;
                case 3:

                    System.out.println("What operation would you like to do?");
                    break;
                case 4: 

                    break;

                case 5: 

                    break;

                case 6:

                    break;
                case 7: 

                    break;
                default:
                    break;
            }
            
        }


        
    }




    private static void tracker_lookup(String choiceType){

    }

    private static void create_newTracker(){

    }

    private static void edit_tracker(String choiceType){

    }

    private static void album_lookup(String choiceType){

    }

    private static void edit_account(String choiceType){

    }

    private static void siteInfo_lookup (String choiceType){

    }




    private static Optional<Normal_User> userLogin(){

        String username="";
        String password="";
        
        System.out.println("Username:");
        input.nextLine();
        username=input.nextLine();
        System.out.println("Password:");
        password=input.nextLine();


        

        return trackerDAO.logIn(username, password);

    }

    private static boolean userCreate(String user_type){
        String username="";
        String password="";
        String security_ans;
        String admin_code="";
        user_type=user_type.toLowerCase();
       
             if(user_type.equals("admin") || user_type.equals("normal")){
                    System.out.println("Username:");
                    username=input.nextLine();
                    System.out.println("Password:");
                    password=input.nextLine();
                    System.out.println("Security Question: Where was the High School You Graduated From?");
                    security_ans=input.nextLine();
                    if(user_type.equals("admin")){
                        System.out.println("ADMIN CODE:");
                        admin_code=input.nextLine();
                    }

                    System.out.println("USERNAME: "+ username + " Password: " + password + " Security answer: " + security_ans);


                    System.out.println("MAKE ACCOUNT? Y/N");
                    if(input.nextLine().toLowerCase().equals("y")){
                        if(user_type.equals("admin")){
                           return trackerDAO.makeAdminUser(username, password, security_ans, admin_code); 
                        }else{
                            return trackerDAO.makeNormalUser(username, password, security_ans);
                        }
                        
                    }else{
                        return false;
                    }
            }else{
                    System.out.println("Invalid Type, only Normal or ADMIN accounts can be made");
            }
        
    
           
        


        return false;
}

}
