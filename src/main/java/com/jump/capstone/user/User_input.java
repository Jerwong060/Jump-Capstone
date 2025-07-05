package com.jump.capstone.user;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.music.music_album;
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
                    trackerDAO.logOut();
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
        boolean good_input=false;

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
                System.out.println("7- Add Album\n");
            }
            
            try{
                choice= input.nextInt();
            }catch(InputMismatchException e){
                System.out.println("NUMBERS ONLY\n");
                input.nextLine();
            }

            switch (choice) {
                case 0:
                    stop=true;
                    user=Optional.empty();
                    bootupPage();
                    break;  
                case 1:
                    while(!good_input){
                     System.out.println("Select The Type of Search: 1-ALL, 2-By ID, 3-By Status\n");

                 try{
                    int choice_search= input.nextInt();
                    tracker_lookup(choice_search,user);
                    good_input=true;
                    }catch(InputMismatchException e){
                    System.out.println("NUMBERS ONLY\n");
                     input.nextLine();
                    }
                }
                    good_input=false;

                    break;
                case 2:
                    boolean error_in_create=create_newTracker(user);

                    if(!error_in_create){
                        System.out.println("Tracker not made");
                    }

                    break;
                case 3:
                while (!good_input) {
                    System.out.println("What operation would you like to do?");
                    System.out.println("1-Change Status");
                    System.out.println("2-Update Listened Count");
                    System.out.println("3-Delete Tracker\n");

                     try{
                        int choice_editTrack= input.nextInt();
                        edit_tracker(choice_editTrack,user);
                        good_input=true;
                    }catch(InputMismatchException e){
                    System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }
                    
                    good_input=false;

                    
                    break;
                case 4: 
                while (!good_input) {
                    System.out.println("1-See all Albums");
                    System.out.println("2-Look at a specific album\n");

                    try{
                        int choice_albumLook= input.nextInt();
                        album_lookup(choice_albumLook);
                        good_input=true;
                    }catch(InputMismatchException e){
                        System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }
                    
                    good_input=false;
                    

                

                    
                    break;

                case 5: 
                while (!good_input) {
                    System.out.println("1-Change Username");
                    System.out.println("2-Change Password");
                    System.out.println("3-Delete Account\n");


                    try{
                        int choice_accountEdit= input.nextInt();
                        edit_account(choice_accountEdit);
                        good_input=true;
                    }catch(InputMismatchException e){
                        System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }
                    
                good_input=false;

                    
                    break;

                case 6:
                while (!good_input) {
                    System.out.println("1-Look at User Count");
                    System.out.println("2-Look at User Created Dates\n");
                    


                    try{
                        int choice_adminInfo= input.nextInt();
                        siteInfo_lookup(choice_adminInfo);;
                        good_input=true;
                    }catch(InputMismatchException e){
                        System.out.println("NUMBERS ONLY\n");
                        input.nextLine();
                    }
                }

                good_input=false;
                    

                    break;
                case 7: 
                    add_album();
                    break;
                default:
                    break;
            }
            
        }


        
    }




    private static void tracker_lookup(int choiceType,Optional<Normal_User> Normal_User){
        boolean good_input=false;

        switch (choiceType) {
            case 1:
                System.out.println("Here are all your Tracked Songs: \n");
                List<user_activity> allTrackers=trackerDAO.getAllActivity(Normal_User.get().getId());

                for (user_activity tracked : allTrackers) {
                    System.out.println(tracked.toString()); 
                }
                break;
            case 2:
            while (!good_input) {
                System.out.println("Enter the Tracker's ID:");

                try {
                    int tracker_id= input.nextInt();
                    Optional<user_activity> tIdSearch = trackerDAO.getActivityByTrackId(tracker_id, Normal_User.get().getId());
                    if(tIdSearch.isPresent()){
                        System.out.println(tIdSearch.get().toString()); 
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
                            System.out.println(trackers.toString()); 
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

            System.out.println(newTracker.toString());

            System.out.println("Make Tracker? Y/N");
            input.nextLine();
            String choice= input.nextLine().toLowerCase();

            if(choice.equals("y")){
                return trackerDAO.createNewTrack(newTracker);
            }
        }
    }

    private static boolean edit_tracker(int choiceType,Optional<Normal_User> user){
        int tracker_change;

        List<user_activity> allTrackers= trackerDAO.getAllActivity(user.get().getId());

        for (user_activity tracker : allTrackers) {
            System.out.println(tracker.toString());
        }

        while(true){
            System.out.println("Enter Track ID of Tracker to Change");
            try {
                tracker_change=input.nextInt();
                System.out.println("Tracker being Changed: " + tracker_change + "\n");
                if(trackerDAO.getActivityByTrackId(tracker_change, user.get().getId()).isEmpty()){
                    System.out.println("Tracker doesn't exist");
                    System.out.println("Try Again with new Tracker ID\n");
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
                System.out.println(tracker.toString());
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

    private static void album_lookup(int choiceType){

    }

    private static void edit_account(int choiceType){

    }

    private static void siteInfo_lookup (int choiceType){

    }

    private static void add_album(){

    }


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
            } 
        }

        

    }

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
            }

        }
        



    }

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
            }
        }
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
