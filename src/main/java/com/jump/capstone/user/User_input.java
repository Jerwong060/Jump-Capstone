package com.jump.capstone.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.music.music_album;
import com.jump.capstone.sql.*;

public class User_input {

    private static Scanner input;
    private static Optional<Normal_User> user=Optional.empty();
    private static DAOInter trackerDAO= new DAOImpli();

    //first page users see when they boot up program
    public static boolean user_terminal(Scanner scan){
        input=scan;
        boolean stop=false;
        
        System.out.println("\nWelcome to Music Track! A WONG Product");

        int choice=-1;
        int attempts=0;
        while(!stop){
            //main menu for bootup page
            while(true){
                System.out.println("1-Login");
                System.out.println("2-Make Account");
                System.out.println("3-Forgot Password");
                System.out.println("4-Exit Program");
                try {
                    choice=input.nextInt();
                    break;
                //prevents non numbers from being assigned
                } catch (InputMismatchException e) {
                    System.out.println("Please Enter either 1,2,3, or 4\n");
                    input.nextLine();
                }
            }

            
            
            try {
                switch (choice) {
                //for when users want to log in 
                case 1:
                    int choice_exit=2;
                    while(true){
                        System.out.println("1-Continue");
                        System.out.println("2-Exit\n");

                        try {
                            choice_exit= input.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            break;
                        }


                    }
                    
                    if(choice_exit==1){
                       user=userLogin(); 
                    }else{
                        break;
                    }
                    
                    
                    if(user.isPresent()){
                        System.out.println("Logged in as " + user.get().getUser_name()+ "\n");
                    }else{
                        System.out.println("login Failed");
                        attempts++;
                    }
                    if(attempts>3){
                        throw new userExceedPasswordAttempts();
                    }
                    break;
                //for when users want to create account
                case 2:
                    input.nextLine();

                    int choice_exit_make=2;
                    while(true){
                        System.out.println("1-Continue");
                        System.out.println("2-Exit\n");

                        try {
                            choice_exit_make= input.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            break;
                        }


                    }
                    
                    if(choice_exit_make==2){
                       break;
                    }


                    System.out.println("Account Type");
                    String user_type=input.nextLine();

                    boolean createResult= userCreate(user_type);

                    if(!createResult){
                        System.out.println("FAILED TO CREATE ACCOUNT\n");
                    }
                    break;
                //for when users want to reset password from bootup screen
                case 3:
                    forgotPassword();
                    break;
                //for when user wants to terminate program
                case 4:
                    trackerDAO.logOut();
                    user=Optional.empty();
                    System.exit(0);
                default:
                    break;
            }
            } catch (userExceedPasswordAttempts e) {
                System.out.println(e.getMessage());
                stop=true;
            }

            if(user.isPresent()){
                mainPage(user);
            }
    }     

        System.out.println("OUTSIDE");
        return true;
    }




    //page that occurs when users log in sucessfully
    private static void mainPage(Optional<Normal_User> user){
        int choice=-1;
        boolean stop_main=false;
        boolean good_input=false;
        
        //main menu
        while(!stop_main){
            System.out.println("What Would You Like To Do?");
            System.out.println("0-Log Out");
            System.out.println("1-Tracker Info");
            System.out.println("2-Track A New Song");
            System.out.println("3-Edit Tracker Infomation");
            System.out.println("4-Look at Albums");
            System.out.println("5-Edit Account Info");
            
            //special options for accounts with admin access
            if(user.get().isAdminAccess()){
                System.out.println("6-Look at Site Statistics");
                System.out.println("7-Add Album\n");
            }
            
            try{
                choice= input.nextInt();
            }catch(InputMismatchException e){
                System.out.println("NUMBERS ONLY\n");
                input.nextLine();
            }

            //special switch case to handle admin actions
            if(user.get().isAdminAccess()){
                switch (choice) {
                    case 6:
                while (!good_input) {
                    System.out.println("1-Look at User Count");
                    System.out.println("2-Look at User Created Dates");
                    System.out.println("3-Exit \n");
                    


                    try{
                        int choice_adminInfo= input.nextInt();
                        if(choice_adminInfo!=4){
                            siteInfo_lookup(choice_adminInfo);
                        }
                        good_input=true;
                    }catch(InputMismatchException e){
                        System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }

                good_input=false;
                    

                    break;
                case 7: 
                     

                    int choice_addAlbum=0;
                    System.out.println("1-Exit");
                    System.out.println("2-Continue\n");
                    try {
                        choice_addAlbum = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input");
                        choice_addAlbum=1;
                    }
                    if( choice_addAlbum != 1){
                        add_album();
                    }
                    break;
                }
            }

            //switch case for normal actions. triggers based on choices
            switch (choice) {
                //logs out user
                case 0:
                    stop_main=true;
                    user=Optional.empty();
                    user_terminal(input);
                    break;
                //for when user wants to see all of their trackers  
                case 1:
                    while(!good_input){
                     System.out.println("Select The Type of Search: 1-ALL, 2-By ID, 3-By Status or 4-Exit \n");

                 try{
                    int choice_search= input.nextInt();
                    if(choice!=4){
                       tracker_lookup(choice_search,user); 
                    }
                    good_input=true;
                    }catch(InputMismatchException e){
                    System.out.println("NUMBERS ONLY\n");
                     input.nextLine();
                    }
                }
                    good_input=false;

                    break;
                //for when user wants to create a new tracker
                case 2:
                    boolean error_in_create=false;
                    int choice_trackerMake=0;
                    System.out.println("1-Exit");
                    System.out.println("2-Continue\n");
                    try {
                        choice_trackerMake = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input");
                        choice_trackerMake=1;
                    }
                    if( choice_trackerMake != 1){
                        error_in_create=create_newTracker(user);
                    }
                    if(!error_in_create){
                        System.out.println("Tracker not made");
                    }

                    break;
                //for when user wants to edit a tracker
                case 3:
                while (!good_input) {
                    System.out.println("What operation would you like to do?");
                    System.out.println("1-Change Status");
                    System.out.println("2-Update Listened Count");
                    System.out.println("3-Delete Tracker");
                    System.out.println("4-Exit\n");

                     try{
                        int choice_editTrack= input.nextInt();
                        if(choice_editTrack!=4){
                            edit_tracker(choice_editTrack,user);
                        }
                        good_input=true;
                    }catch(InputMismatchException e){
                    System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }
                    
                    good_input=false;

                    
                    break;
                //for when user wants to see albums or give a rating to an album 
                case 4: 
                while (!good_input) {
                    System.out.println("1-See all Albums");
                    System.out.println("2-Look at a specific album");
                    System.out.println("3-Give Rating");
                    System.out.println("4-Exit \n");

                    try{
                        int choice_albumLook= input.nextInt();
                        if(choice_albumLook!=4){
                           album_lookup(choice_albumLook); 
                        }
                        good_input=true;
                    }catch(InputMismatchException e){
                        System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }
                    
                    good_input=false;
                    

                

                    
                    break;

                //For when account changes are needed
                case 5: 
                while (!good_input) {
                    System.out.println("1-Change Username");
                    System.out.println("2-Change Password");
                    System.out.println("3-Delete Account");
                    System.out.println("4-Exit \n");


                    try{
                        int choice_accountEdit= input.nextInt();
                        if(choice_accountEdit!=4){
                          edit_account(choice_accountEdit);
                        }
                        good_input=true;
                    }catch(InputMismatchException e){
                        System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }
                    
                good_input=false;

                    
                    break;
                default:
                    break;
            }
            
        }


        
    }



    //handles getting data from DAO and showing the data
    private static void tracker_lookup(int choiceType,Optional<Normal_User> Normal_User){
        boolean good_input=false;

        
        switch (choiceType) {
            //when user wants to see all trackers
            case 1:
                System.out.println("Here are all your Tracked Songs: \n");
                List<user_activity> allTrackers=trackerDAO.getAllActivity(Normal_User.get().getId());

                for (user_activity tracked : allTrackers) {
                    System.out.println(tracked.toString(trackerDAO)); 
                }
                break;
            //when user wants to see a tracker based on id
            case 2:
            while (!good_input) {
                System.out.println("Enter the Tracker's ID:");

                try {
                    int tracker_id= input.nextInt();
                    Optional<user_activity> tIdSearch = trackerDAO.getActivityByTrackId(tracker_id, Normal_User.get().getId());
                    if(tIdSearch.isPresent()){
                        System.out.println(tIdSearch.get().toString(trackerDAO)); 
                    }else{
                        System.out.println("No Tracker by that ID");
                    }
                    good_input=true;
                } catch (InputMismatchException e) {
                    System.out.println("NUMBERS ONLY");
                    input.nextLine();
                }
                
            }

                good_input=false;
                break;
            //when a user wants to see trackers based on status
            case 3:
                while (!good_input) {
                    System.out.println("Enter Status you would like to Track");
                    System.out.println("1-Not Started");
                    System.out.println("2-In Progress");
                    System.out.println("3-Completed");

                    try {
                        int status= input.nextInt();
                        List<user_activity> byStatus= trackerDAO.getActivityByStatus(status, Normal_User.get().getId());
                        for (user_activity trackers : byStatus) {
                            System.out.println(trackers.toString(trackerDAO)); 
                        }
                        good_input=true;
                    } catch (InputMismatchException e) {
                        System.out.println("NUMBERS ONLY");
                        input.nextLine();
                    }
                }

                good_input=false;
                break;
            default:
                break;
        }

        System.out.println("\n");

    }

    //for interacting with DAO when creating a new tracker. Takes in user inputs and then attempts to commit changes using DAO.
    private static boolean create_newTracker(Optional<Normal_User> user){
        while(true){
            int album = album_to_track();

            int status= status_Album();

            int listened_count;

            if(status==2){
                listened_count= listened(album);
            }else if(status==1){
                listened_count=0;
            }else{
                listened_count=trackerDAO.getAlbumByid(album).get().getAlbum_songcount();
            }
            

            int max_albums=trackerDAO.getAlbumByid(album).get().getAlbum_songcount();

            user_activity newTracker= new user_activity(user.get().getId(),album,status,listened_count,max_albums);

            System.out.println(newTracker.noIdString(trackerDAO));

            System.out.println("Make Tracker? Y/N");
            input.nextLine();
            String choice= input.nextLine().toLowerCase();

            if(choice.equals("y")){
                return trackerDAO.createNewTrack(newTracker);
            }
        }
    }


    //edits tracker on songs listened to, status, or deletes a tracker
    private static boolean edit_tracker(int choiceType,Optional<Normal_User> user){
        int tracker_change;

        List<user_activity> allTrackers= trackerDAO.getAllActivity(user.get().getId());

        for (user_activity tracker : allTrackers) {
            System.out.println(tracker.toString(trackerDAO));
        }

        while(true){
            System.out.println("Enter Track ID of Tracker to Change");
            try {
                tracker_change=input.nextInt();
                System.out.println("Tracker being Changed: " + tracker_change + "\n");
                if(!trackerDAO.getActivityByTrackId(tracker_change, user.get().getId()).isPresent()){
                    System.out.println("Tracker doesn't exist");
                    System.out.println("Try Again with new Tracker ID\n");
                    choiceType=-1;
                    break;
                }else{
                    break;
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Numbers Only");
            }
        }

        switch (choiceType) {
            case 1:
                int status;
                while(true){
                    System.out.println("What is new Status?");
                    System.out.println("1-Not started");
                    System.out.println("2-In Progress");
                    System.out.println("3-Completed\n");

                    try {
                        status=input.nextInt();
                        if(status>3||status<1){
                            System.out.println("Invalid Status Code");
                        }else{
                          break;  
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Numbers Only");
                    }
                }

                if(status==1){
                    trackerDAO.setListened_count(tracker_change, 0, user.get());
                }else if(status==2){
                    int songs_listened;
                    while(true){
                        System.out.println("How Many Songs Listened To?");
                        try {
                            songs_listened= input.nextInt();
                            int max= trackerDAO.getAlbumByid(trackerDAO.getActivityByTrackId(tracker_change, user.get().getId()).get().getAlbum_id()).get().getAlbum_songcount();
                            if(max<songs_listened){
                                System.out.println("Exceeded Max of Songs in Album");
                                System.out.println("Enter new Song Count\n");
                            }else{
                                trackerDAO.setListened_count(tracker_change,songs_listened, user.get());
                                break;
                            }
                            
                        } catch (Exception e) {
                            System.out.println("Numbers Only");
                        }

                    }
                        
                }else{
                    int songsMax=trackerDAO.getAlbumByid(trackerDAO.getActivityByTrackId(tracker_change, user.get().getId()).get().getAlbum_id()).get().getAlbum_songcount();
                    trackerDAO.setListened_count(tracker_change,songsMax,user.get());
                }

                return trackerDAO.setStatus(tracker_change, status, user.get());
            case 2:
                int songs;
                Optional<user_activity> tracker = trackerDAO.getActivityByTrackId(tracker_change, user.get().getId());
                int song_max= trackerDAO.getAlbumByid(tracker.get().getAlbum_id()).get().getAlbum_songcount();
                System.out.println(tracker.get().toString(trackerDAO));
                System.out.println("How many songs listened to?");

                while(true){
                    try {
                        songs=input.nextInt();

                        if(songs<0){
                            System.out.println("Song Count can't be negative\n");
                        }else if (songs>song_max){
                            System.out.println("Song count exceeds songs on Album, Try Again\n");
                        }else if (songs==0){
                            trackerDAO.setStatus(tracker_change, 1, user.get());
                            return trackerDAO.setListened_count(tracker_change, songs, user.get());
                        }else if(songs==song_max){
                            trackerDAO.setStatus(tracker_change, 3, user.get());
                            return trackerDAO.setListened_count(tracker_change, song_max, user.get());
                        }else{
                            trackerDAO.setStatus(tracker_change, 2, user.get());
                            return trackerDAO.setListened_count(tracker_change, songs, user.get());
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Numbers Only");
                    }
                }

            case 3:
                return trackerDAO.deleteTrack(tracker_change, user.get());

            default:
                return false;
        }
    }

    //looks up all albums, a specific album, or gives a rating to album. Handles the DAO calls
    private static void album_lookup(int choiceType){

        switch (choiceType) {
            case 1:
                 List<music_album> albums= trackerDAO.listAllalbums();

                for (music_album album: albums) {
                    System.out.println(album.toString()+ "\n");
                
                }
                System.out.println("\n");
                break;
            case 2:
                List<music_album> album_idList= trackerDAO.listAllalbums();
                
                while(true){
                    
                    for (music_album music_album : album_idList) {
                        System.out.println("ID: " + music_album.getAlbum_id() + " Name: " + music_album.getAlbum_name());
                    }

                    System.out.println("\n");

                    System.out.println("Please Enter Id of Album");
                    try {
                        int id= input.nextInt();

                        Optional<music_album> album= trackerDAO.getAlbumByid(id);

                     if(album.isPresent()){
                            System.out.println(album.get().toString() + "\n");
                        }else{
                            System.out.println("Album Not Found");
                        }
                    
                        break;
                    } catch (InputMismatchException e) {
                    System.out.println("Numbers Only");
                    }
                    }
                break;

            case 3:
                
                 int album_id=0;
                 double rating=0.00;
                 boolean result=false;

                while(true){
                    System.out.println("Enter Album ID");
                    try {
                         album_id= input.nextInt();
                        System.out.println("Enter Rating EXAMPLE: 3.57");
                         rating= input.nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Numbers only");
                    }

                    if(rating>5.00 || rating <0.00){
                        System.out.println("Rating Too High or Too Low, 0-5 only");
                    }else{
                        result= trackerDAO.giveRating(album_id, rating);
                    }
                    if(result){
                        break;
                    }else{
                        System.out.println("Rating Failed to Add");
                    }
                }
                break;
            default:
                break;
        }
           
    }

    
    //edits account infomation by calling the DAO. Deletes account, changes user name or password
    private static void edit_account(int choiceType){
        switch (choiceType) {
            case 1:
                
                while(true){
                    input.nextLine();
                    System.out.println("Please Enter New Username(CASE SENSITIVE)");

                    String new_user= input.nextLine();

                    System.out.println("Changing Username to: "+ new_user);

                    System.out.println("Change? y/n");

                    String choice = input.nextLine().toLowerCase();

                    if(choice.equals("y")){
                        boolean changed = trackerDAO.renameUsername(user.get(), new_user);

                        if (!changed) {
                            System.out.println("Username Not Changed, Error Occured");
                        }
                        break;
                    }else{

                        System.out.println("1-Retry \n 2-Exit");
                        int choice_num;
                        try {
                            choice_num = input.nextInt();
                        } catch (InputMismatchException e) {
                            break;
                        }

                        if(choice_num==2){
                            mainPage(user);
                        }
                    
                    }

                }


                break;
            case 2: 
                while (true) {
                    input.nextLine();
                    System.out.println("Enter New Password: ");
                    String password_new= input.nextLine();
                    System.out.println("Reenter Password: ");
                    String reentry_pass= input.nextLine();
                    boolean changed=false;
                    if(password_new.equals(reentry_pass)){
                        changed=trackerDAO.changePasswordLoggedIn(password_new, user.get());
                    }else{
                        System.out.println("Passwords Don't Match");
                    }

                    if(changed){
                        break;
                    }else{
                        System.out.println("Password change FAILED ");
                        break;
                    }
                }
                break;
            
            case 3: 

                System.out.println("Type Username to Start Delection or Exit to Stop Delection");
                input.nextLine();
                
                String delete_authorization= input.nextLine();

                if(delete_authorization.equals(user.get().getUser_name())){
                    trackerDAO.deleteUser(user.get());
                    user_terminal(input);
                }

                break;
            default:
                break;
        }

    }


    //for admins to look at site infomation. interacts with DAO for these operations 
    private static void siteInfo_lookup (int choiceType){

        switch (choiceType) {
            case 1:
                System.out.println("There are: " + trackerDAO.numberofUsers(user.get().isAdminAccess()) + " USERS\n" );
                break;
            case 2:
                List<Normal_User> user_list= trackerDAO.allUserInfo(user.get().isAdminAccess());
                for (Normal_User user : user_list) {
                    System.out.println(user.toString());
                }
                System.out.println("\n");
                break;
            default:
                break;
        }

    }

    //adds an album to the database
    private static void add_album(){

        String artist;
        String name;
        int song_count;
        String genre;
        LocalDate release;
         boolean made=false;
    
        while(true){
            input.nextLine();
            System.out.println("Album: ");
            name=input.nextLine();

            System.out.println("Enter Artist Name");
            artist = input.nextLine();

            System.out.println("Enter Genre:");

            genre= input.nextLine();



            while(true){
                try {
                    System.out.println("Enter song count");
                    song_count=input.nextInt();
                    if(song_count<=0){
                        System.out.println("Song Count too low");
                    }else{
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Songs can only be whole numbers");
                    input.nextLine();
                    
            }
            
        }
            input.nextLine();

            while(true){

                try {

                System.out.println("Enter Release Date. Example: August 10-2021 ");

                SimpleDateFormat fromUser = new SimpleDateFormat("MMMM dd-yyyy");

                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

                String date= input.nextLine();
                
                String reformattedStr = myFormat.format(fromUser.parse(date));

                release=LocalDate.parse(reformattedStr);

                

                if(release.isBefore(LocalDate.now())){
                    break;
                }else{
                    System.out.println("Date Can't be in the Future");
                }
                
            } catch (ParseException e) {
                System.out.println("Incorrect Format");
            }catch(DateTimeParseException e){
                System.out.println("Incorrect Date Format,");
            }
            }
            

            break;
        }

         music_album newOne= new music_album(artist,name,song_count,0,genre,0.00,release);

         System.out.println(newOne.noIdString());

         System.out.println("Create? y/n");

         String choice= input.nextLine().toLowerCase();

         if(choice.equals("y")){
            made = trackerDAO.addAlbum(newOne, user.get().isAdminAccess());
         }

         if(!made){
            System.out.println("Song Not Added");
         }
    }


    //gets the user inputs for songs listened to
    private static int listened(int album_id){

        System.out.println("How many Songs listened to:");
        
        while (true) {
           try {
            int songs_listened=input.nextInt();


            int max_albums = trackerDAO.getAlbumByid(album_id).get().getAlbum_songcount();

            if(songs_listened>max_albums){
                System.out.println("Exceeded maximum song count for Album, use a lower number");
            }else if(songs_listened<0){
                System.out.println("Songs listened to can't be negative");
            }else{
                return songs_listened;
            }
            } catch (InputMismatchException e) {
                System.out.println("Numbers Only");
                input.nextLine();
            } 
        }

        

    }

    //gets the status of an album from user
    private static int status_Album(){

        while(true){
            System.out.println("1-Not Started");
            System.out.println("2-In Progress");
            System.out.println("3-Completed\n");

            System.out.println("Enter Status:\n");

            try {
                int status=input.nextInt();
                if(status<1 || status>3){
                    System.out.println("Not a Valid Option");
                }else{
                    return status;
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Numbers Only");
                input.nextLine();
            }

        }
        



    }


    //gets the album the user wants to track
    private static int album_to_track(){
       
       int largest_id=1;
       List<music_album> all_albums = trackerDAO.listAllalbums();
       for (music_album album : all_albums) {
            System.out.println(album.getAlbum_id()+" "+album.getAlbum_name());
            largest_id=album.getAlbum_id();
       }
        while(true){
            System.out.println("Enter ID of Album to add\n");

            try {
                int album_id=input.nextInt();

                if(album_id<=largest_id){
                    return album_id;
                }else{
                    System.out.println("Album with that ID doesn't exist\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Number Only\n");
                input.nextLine();
            }
        }
    }




    //gets log in infomation from user and sends it to DAO
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


    //for when a user wants to create an account
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


    //for when a user wants to reset password without logging in. Takes in Security Answer to question, checks it, and if correct allows user to enter new password. 
    private static void forgotPassword(){
                    while (true){
                        input.nextLine();
                        System.out.println("Exit y/n");
                        String exit= input.nextLine().toLowerCase();

                        if(exit.equals("y")){
                            user_terminal(input);
                        }

                        System.out.println("Enter Username: ");
                        String user_name= input.nextLine();
                        System.out.println("Where was the Highschool You Graduated from?");
                        String answer= input.nextLine();

                        boolean checkAns= trackerDAO.checkSecurityQuestion(answer, user_name);

                        if(checkAns){
                            System.out.println("New Password");
                            String password= input.nextLine();
                            System.out.println("Repeat Password");
                            String repeat = input.nextLine();

                            if(!password.equals(repeat)){
                                System.out.println("Passwords doesn't match");
                            }else{
                                boolean changed = trackerDAO.changePasswordBootScreen(password, user_name);

                                if(!changed){
                                    System.out.println("Password not Changed");
                                }else{
                                    break;
                                }
                            }
                        }
                        
                       

                    }
                   
    }
}
