package com.jump.capstone.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jump.capstone.connection.ConnectionManager;
import com.jump.capstone.exceptions.userAlreadyExists;
import com.jump.capstone.music.music_album;
import com.jump.capstone.security.Password_Handler;
import com.jump.capstone.user.Normal_User;
import com.jump.capstone.user.user_activity;

public class DAOImpli implements DAOInter {

    private Connection connection = null;

	//gets all the trackers associated with a user, taking in the user_id. 
    @Override
    public List<user_activity> getAllActivity(int user_id){
        List<user_activity> allActivity = new ArrayList<>();
        
        try{

			connection=ConnectionManager.getConnection();
            //statement for getting all trackers based on ID
			PreparedStatement getAllStatement=connection.prepareStatement("SELECT * FROM activity where user_id = ? ");

            getAllStatement.setInt(1, user_id);

			ResultSet results= getAllStatement.executeQuery();
            //iterates over the results
			while(results.next()){
                int track_id= results.getInt(1);
                int user = results.getInt(2);
                int album_id= results.getInt(3);
                int status = results.getInt(4);
                int listened_count= results.getInt(5);
                int total_songs = results.getInt(6);

                //creates the user_activity before adding it to arraylist for return
				user_activity activity= new user_activity(track_id,user, album_id, status, listened_count, total_songs);

				allActivity.add(activity);
			}
				return allActivity;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
    }

    //lists all albums in the database
    @Override
    public List<music_album> listAllalbums(){
        List<music_album> allAlbums= new ArrayList<>();

        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getAllAlbumsStatement=connection.prepareStatement("SELECT * FROM album");

           

			ResultSet results= getAllAlbumsStatement.executeQuery();

			while(results.next()){
                int id= results.getInt(1);
                String artist= results.getString(2);
                String album_name= results.getString(3);
                int song_count= results.getInt(4);
                int rating_count= results.getInt(5);
                String genre= results.getString(6);
                Double rating_percent= results.getDouble(7);
                LocalDate date_released= results.getDate(8).toLocalDate();

                music_album album=new music_album(id, artist, album_name,song_count,rating_count,genre,rating_percent,date_released);

                allAlbums.add(album);

				
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}

        return allAlbums;
    }

    //gets back an album's info based on its id, if an invalid id gives back empty optional, if not returns an optional of that album.
    @Override
    public Optional<music_album> getAlbumByid(int album_id){

        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getAlbumIDStatement=connection.prepareStatement("SELECT * FROM album WHERE album_id= ?");

            getAlbumIDStatement.setInt(1, album_id);
           

			ResultSet results= getAlbumIDStatement.executeQuery();

			while(results.next()){
                int id= results.getInt(1);
                String artist= results.getString(2);
                String album_name= results.getString(3);
                int song_count= results.getInt(4);
                int rating_count= results.getInt(5);
                String genre= results.getString(6);
                Double rating_percent= results.getDouble(7);
                LocalDate date_released= results.getDate(8).toLocalDate();

                music_album album=new music_album(id, artist, album_name,song_count,rating_count,genre,rating_percent,date_released);

                return Optional.of(album);

				
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}

        return Optional.empty();
    }

    //gets tracker back based on the Tracker ID, gives empty optional if not exists. if exists, then optional of that tracker. 
    @Override
    public Optional<user_activity> getActivityByTrackId(int TrackId,int user_id){
        
        user_activity activity;
        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getTrackStatement=connection.prepareStatement("SELECT * FROM activity where track_id = ? and user_id=?");

            getTrackStatement.setInt(1, TrackId);
            getTrackStatement.setInt(2, user_id);

			ResultSet results= getTrackStatement.executeQuery();

			if(results.next()){
                int track_id = results.getInt(1);
                int user = results.getInt(2);
                int album_id= results.getInt(3);
                int status = results.getInt(4);
                int listened_count= results.getInt(5);
                int total_songs = results.getInt(6);

			    activity= new user_activity(track_id, user, album_id, status, listened_count, total_songs);

			
			    return Optional.of(activity);
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}




        return Optional.empty();
    }


    

    //Gets back all tracks with a specific state: not started, in progress, and completed
    @Override
    public List<user_activity> getActivityByStatus(int status,int user_id){
        List<user_activity> byStatus = new ArrayList<>();
        
        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getStatusStatement=connection.prepareStatement("SELECT * FROM activity where status = ?  AND user_id= ?");

            getStatusStatement.setInt(1, status);
            getStatusStatement.setInt(2,user_id);


			ResultSet results= getStatusStatement.executeQuery();

			while(results.next()){
                
                int track_id = results.getInt(1);
                int album_id= results.getInt(3);
                int listened_count= results.getInt(5);
                int total_songs = results.getInt(6);

				user_activity activity= new user_activity(track_id, user_id, album_id, status, listened_count, total_songs);

				byStatus.add(activity);
			}
				return byStatus;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}




        return null;
    }


    //gets back the username of a logged in account. 
    @Override
    public String getAccountUser(int user_id){

         try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getUserInfoStatement=connection.prepareStatement("SELECT * FROM user WHERE user_id=?");

            
            getUserInfoStatement.setInt(1, user_id);

			ResultSet results= getUserInfoStatement.executeQuery();

			if(results.next()){
                String user_name = results.getString(2);
			
			    return user_name;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}




        return " ";
    }
    

    //sets a status of an already made tracker. Gives back true if status was changed. If not, false.  
    @Override
    public boolean setStatus(int track_id,int status,Normal_User user){

        
        try{

			connection=ConnectionManager.getConnection();

            Optional<user_activity> doesExist= getActivityByTrackId(track_id,user.getId());

            if(doesExist.isPresent()){

            

			    PreparedStatement setStatusStatement=connection.prepareStatement("UPDATE activity SET status = ? WHERE track_id=? and user_id=?");

                setStatusStatement.setInt(1, status);
                setStatusStatement.setInt(2, track_id);
                setStatusStatement.setInt(3, user.getId());

			    int results= setStatusStatement.executeUpdate();

			    if(results>-1){

                    String status_message=""; 

                    switch (status) {
                     case 1:
                            status_message = "Not Completed";
                            break;
                    
                        case 2:
                            status_message="In Progress";
                            break;
                      case 3: 
                            status_message= "Completed";
                            break;
                }
                
                    System.out.printf("Status changed on Tracker %d to %s\n",track_id,status_message);

			        return true;
              }
            
            }
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}

    
        return false;
    }


    //makes a new tracker.
    @Override
    public boolean createNewTrack(user_activity activity){

        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement createTrackerStatement=connection.prepareStatement("INSERT INTO activity values(?,?,?,?,?,?)");

            createTrackerStatement.setNull(1, java.sql.Types.INTEGER);

            createTrackerStatement.setInt(2, activity.getUser_id());

            createTrackerStatement.setInt(3, activity.getAlbum_id());

            createTrackerStatement.setInt(4, activity.getStatus());

            createTrackerStatement.setInt(5, activity.getListened_count());

            createTrackerStatement.setInt(6, activity.getTotal_songs());


			int results= createTrackerStatement.executeUpdate();

			if(results>-1){

                System.out.println("New Tracker has been created\n");

			    return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }

   

    //adds a new album to database, needs admin permissions
    @Override
    public boolean addAlbum(music_album album,boolean admin_access){

     if(admin_access){
        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement addAlbumStatement=connection.prepareStatement(
                "INSERT INTO album VALUES(?,?,?,?,?,?,?,?)");

            addAlbumStatement.setNull(1, java.sql.Types.INTEGER);
            addAlbumStatement.setString(2, album.getAlbum_artist());
            addAlbumStatement.setString(3, album.getAlbum_name());
            addAlbumStatement.setInt(4, album.getAlbum_songcount());
            addAlbumStatement.setInt(5, album.getAlbum_ratingcount());
            addAlbumStatement.setString(6, album.getAlbum_genre());
            addAlbumStatement.setDouble(7, album.getAlbum_rating());
            addAlbumStatement.setDate(8, java.sql.Date.valueOf(album.getAlbum_release()));


			int results= addAlbumStatement.executeUpdate();

			if(results>-1){
                System.out.printf("%s has been added to the Database\n", album.getAlbum_name());
                return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
    }else{
        System.out.println("NO ACCESS\n");
    }


        return false;
    }


    //deletes a tracker
    @Override
    public boolean deleteTrack(int track_id,Normal_User user){

         try{

			connection=ConnectionManager.getConnection();

			PreparedStatement deleteTrackStatement=connection.prepareStatement(
                "DELETE FROM activity WHERE track_id=? and user_id=?");

                deleteTrackStatement.setInt(1, track_id);
                deleteTrackStatement.setInt(2, user.getId());
         


			int results= deleteTrackStatement.executeUpdate();

			if(results>-1){
                System.out.printf("%d has been deleted\n", track_id);
                return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}





        return false;
    }

    //give a rating to an album. Does an average of rating and also increments the amount of ratings by one.
    @Override
    public boolean giveRating(int album_id,Double rating){

        try{
            
                PreparedStatement setRatingStatement=connection.prepareStatement(
                    "UPDATE album SET album_rating = (?+album_rating)/(album_ratingcount+1), album_ratingcount= album_ratingcount + 1  WHERE album_id=?");

               
                setRatingStatement.setDouble(1, rating);
                
                setRatingStatement.setInt(2, album_id);

                
			    int results= setRatingStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("Rating changed\n");

			        return true;
                }
            
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}


        return false;
    }

    //sets the listened songs of an album to another value
    @Override
    public boolean setListened_count(int track_id,int count,Normal_User user){


         try{
            int songCount=-1;

			connection=ConnectionManager.getConnection();


           PreparedStatement getAlbumSongsStatement= connection.prepareStatement("SELECT album.album_songcount FROM activity\n" + //
                              "INNER JOIN album on activity.album_id = album.album_id\n" + //
                              "WHERE activity.track_id=? ");

            getAlbumSongsStatement.setInt(1,track_id);

            ResultSet countSongs= getAlbumSongsStatement.executeQuery();


            if(countSongs.next()){
                songCount=countSongs.getInt(1);        
            }


            if(songCount>=count){
                PreparedStatement setCountStatement=connection.prepareStatement("UPDATE activity SET listened_count = ? WHERE track_id=? and user_id=?");

               
                setCountStatement.setInt(1, count);
                
                setCountStatement.setInt(2, track_id);

                setCountStatement.setInt(3, user.getId());

			    int results= setCountStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("Changed Song Count to %d\n", count);

			        return true;
                }
            }
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}


        return false;
    }

    //deletes a user
    @Override
    public boolean deleteUser(Normal_User user){
        try{
			connection=ConnectionManager.getConnection();


           
    
                PreparedStatement deleteUserStatement=connection.prepareStatement("DELETE FROM user where user_name = ? ");

                deleteUserStatement.setString(1, user.getUser_name());

			    int results= deleteUserStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("USER DELETED\n");

			        return true;
                }
            
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }
    


    //changes a password while logged into an account
    @Override
    public boolean changePasswordLoggedIn(String password,Normal_User user){

     try{
                PreparedStatement setCountStatement=connection.prepareStatement("UPDATE user SET user_pass = ? WHERE user_id=?");

                setCountStatement.setString(1, Password_Handler.password_worker_access(password));

                setCountStatement.setInt(2, user.getId());

			    int results= setCountStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("Password Changed\n");

			        return true;
                }
            
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }


    //checks security question when changing password while not logged in.
    @Override 
    public boolean checkSecurityQuestion(String answer,String user_name){
        try{
            boolean allowed=false;

			connection=ConnectionManager.getConnection();


           PreparedStatement getAnsStatement= connection.prepareStatement("SELECT user_ans FROM user WHERE user_name = ?");

            getAnsStatement.setString(1,user_name);

            ResultSet securityAns= getAnsStatement.executeQuery();


            
            while (securityAns.next()) {
                if(Password_Handler.password_checker_access_ans(answer, securityAns.getString(1))){
                
                    allowed=true;
                }     
            }

            
            

            if(allowed){
               return true;
            }
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
        return false;

    }




    //Changes password when not logged in.
    @Override
    public boolean changePasswordBootScreen(String password,String user_name){

     try{
            
                PreparedStatement setPassStatement=connection.prepareStatement("UPDATE user SET user_pass = ? WHERE user_name=?");

                setPassStatement.setString(1, Password_Handler.password_worker_access(password));

                setPassStatement.setString(2, user_name);

			    int results= setPassStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("Password Changed\n");

			        return true;
                }
            
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }

    //creates a normal user
    public boolean makeNormalUser(String user_name, String password,String securityAns){

         try{
            
            int existing=-1;
			connection=ConnectionManager.getConnection();


           PreparedStatement getUsernameStatement= connection.prepareStatement("SELECT COUNT(user_name) FROM user WHERE user_name= ?");



            getUsernameStatement.setString(1,user_name);

            ResultSet username= getUsernameStatement.executeQuery();

            if(username.next()){
                existing = username.getInt(1);
            }





            
            

            if(existing == 0){
                PreparedStatement createUserPreparedStatement=connection.prepareStatement("Insert INTO user Values(NULL,?,?,?,?,?)");

                createUserPreparedStatement.setString(1, user_name);

                createUserPreparedStatement.setString(2, Password_Handler.password_worker_access(securityAns));

                createUserPreparedStatement.setString(3, Password_Handler.password_worker_access(password));

                createUserPreparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

                createUserPreparedStatement.setBoolean(5, false);


			    int results= createUserPreparedStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("User Created, Please Log in\n");

			        return true;
                }
            }else{
                throw new userAlreadyExists(user_name);
            }
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(userAlreadyExists e){
            System.out.println(e.getMessage());
        }catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }

    //creates an admin account, needs admin code to work
    public boolean makeAdminUser(String user_name, String password,String securityAns,String admString){


         try{
            if(Password_Handler.password_Checker_Access_Admin(admString)){
                int existing=-1;
			    connection=ConnectionManager.getConnection();


                PreparedStatement getUsernameStatement= connection.prepareStatement("SELECT COUNT(user_name) FROM user WHERE user_name= ?");



                getUsernameStatement.setString(1,user_name);

                ResultSet username= getUsernameStatement.executeQuery();

             if(username.next()){
                    existing = username.getInt(1);
             }


                if(existing == 0){
                    PreparedStatement createADMINUserPreparedStatement=connection.prepareStatement("Insert INTO user Values(NULL,?,?,?,?,?)");

                    createADMINUserPreparedStatement.setString(1, user_name);

                    createADMINUserPreparedStatement.setString(2, Password_Handler.password_worker_access(securityAns));

                    createADMINUserPreparedStatement.setString(3, Password_Handler.password_worker_access(password));

                    createADMINUserPreparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

                    createADMINUserPreparedStatement.setBoolean(5, true);


			        int results= createADMINUserPreparedStatement.executeUpdate();

			        if(results>-1){

                        System.out.printf("User Created, Please Log in\n");

			         return true;
                 }
                }else{
                 throw new userAlreadyExists(user_name);
                }
            }else{
                System.out.println("INVALID ADMIN ACCESS CODE\n");
            }
            
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(userAlreadyExists e){
            System.out.println(e.getMessage());
        }catch(Exception e){
			e.printStackTrace();
		}




        System.out.println("User Creation Failed");
        return false;
    }

    //logs in a user by checking username and password
    public Optional<Normal_User> logIn(String username,String password){
        
        try{
            if(Password_Handler.password_checker_access(password, username)){    
		        connection=ConnectionManager.getConnection();
           
                PreparedStatement loginPreparedStatement=connection.prepareStatement("SELECT * FROM user WHERE user_name= ? COLLATE utf8mb4_0900_as_cs");

                loginPreparedStatement.setString(1, username);
               

               

			    ResultSet results= loginPreparedStatement.executeQuery();

                if(results.next()){
                    int id=results.getInt(1);
                    String user_name= results.getString(2);
                    String user_ans= results.getString(3);
                    String user_pass = results.getString(4);
                    LocalDate user_create = results.getDate(5).toLocalDate();
                    boolean admin_access= results.getBoolean(6);
                    
                    Normal_User loggedIn = new Normal_User(user_name, user_pass, user_ans,user_create,id,admin_access);

                    return Optional.of(loggedIn);
                   
                }
            }
		}catch(SQLException e){
			System.out.println(e.getMessage());
        }catch(Exception e){
			e.printStackTrace();
		}


        return Optional.empty();

        
    }

    

    //gives back how many users are in the database 
    public int numberofUsers(boolean admin){

        if(admin){

            try{
            
		        connection=ConnectionManager.getConnection();
           
                PreparedStatement countUsersPreparedStatement=connection.prepareStatement("SELECT COUNT(user_id) FROM user");

			    ResultSet results= countUsersPreparedStatement.executeQuery();

                if(results.next()){

                    return results.getInt(1);
                   
                }
		    }catch(SQLException e){
			System.out.println(e.getMessage());
            }catch(Exception e){
			    e.printStackTrace();
		    }

        }else{
            System.out.println("NO ACCESS\n");
        }



        return -1;
    }


    //gives infomation on all users in database
    public List<Normal_User> allUserInfo(boolean admin){

        ArrayList<Normal_User> list_users= new ArrayList<Normal_User>();

        if(admin){

            try{
            
		        connection=ConnectionManager.getConnection();
           
                PreparedStatement allUsersPreparedStatement=connection.prepareStatement("SELECT * FROM user");

			    ResultSet results= allUsersPreparedStatement.executeQuery();

                while(results.next()){
                    int id= results.getInt(1);
                    String name= results.getString(2);
                    LocalDate dateMade= results.getDate(5).toLocalDate();
                    boolean type= results.getBoolean(6);
                    
                   Normal_User temp= new Normal_User(id,name,dateMade,type);

                    list_users.add(temp);
                    
                }

                return list_users;
		    }catch(SQLException e){
			System.out.println(e.getMessage());
            }catch(Exception e){
			    e.printStackTrace();
		    }

        }else{
            System.out.println("NO ACCESS\n");
        }



    


        return list_users;
    }

    //changes username of an account
    public boolean renameUsername(Normal_User user,String userName){

      
        try{
            
		        connection=ConnectionManager.getConnection();
           
                PreparedStatement renameUserPreparedStatement=connection.prepareStatement("UPDATE user SET user_name = ? WHERE user_id=?");

                renameUserPreparedStatement.setString(1, userName);
                renameUserPreparedStatement.setInt(2, user.getId());


			    int results= renameUserPreparedStatement.executeUpdate();

                if(results>-1){
                    System.out.println("USERNAME CHANGED TO "+ userName + "\n");
                    return true;
                }

                return true;
		    }catch(SQLException e){
			System.out.println(e.getMessage());
            }catch(Exception e){
			    e.printStackTrace();
		    }


        return false;
    }




    //closes the connection to database
    @Override
    public void logOut(){
        try {
            ConnectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
