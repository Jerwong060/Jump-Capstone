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
import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.music.music_album;
import com.jump.capstone.security.Password_Handler;
import com.jump.capstone.user.Admin_User;
import com.jump.capstone.user.Normal_User;
import com.jump.capstone.user.user_activity;

public class DAOImpli implements DAOInter {

    private Connection connection = null;

	@Override
	public void makeConnection() throws ClassNotFoundException, SQLException {
		
		if(connection == null) {
			connection = ConnectionManager.getConnection();
		}
	}
	
	@Override
	public void closeConnection() throws SQLException {
		connection.close();
	}


    @Override
    public List<user_activity> getAllActivity(int user_id){
        List<user_activity> allActivity = new ArrayList<>();
        
        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getAllStatement=connection.prepareStatement("SELECT * FROM activity where user_id = ? ");

            getAllStatement.setInt(1, user_id);

			ResultSet results= getAllStatement.executeQuery();

			while(results.next()){
                
                int track_id = results.getInt(1);
                int user = results.getInt(2);
                int album_id= results.getInt(3);
                int status = results.getInt(4);
                int listened_count= results.getInt(5);
                double listened_percent = results.getDouble(6);

				user_activity activity= new user_activity(track_id, user, album_id, status, listened_count, listened_percent);

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
                double listened_percent = results.getDouble(6);

			    activity= new user_activity(track_id, user, album_id, status, listened_count, listened_percent);

			
			    return Optional.of(activity);
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}




        return Optional.empty();
    }


    


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
                double listened_percent = results.getDouble(6);

				user_activity activity= new user_activity(track_id, user_id, album_id, status, listened_count, listened_percent);

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


    @Override

    public Double getAvgListened(int user_id){
     
         try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getAVGStatement=connection.prepareStatement("SELECT AVG(listened_percent) FROM activity where user_id= ?");

            getAVGStatement.setInt(1, user_id);

			ResultSet results= getAVGStatement.executeQuery();

			if(results.next()){
                
                double listened_percent = results.getDouble(1);

			    return listened_percent;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}

        
        return 000.00;
    }
    
    @Override
    public int getListened_count(int album_id,int user_id){

    try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getCountstatement=connection.prepareStatement("SELECT listened_count FROM activity where album_id= ? and user_id= ?");

            getCountstatement.setInt(1, album_id);

            getCountstatement.setInt(2, user_id);

			ResultSet results= getCountstatement.executeQuery();

			if(results.next()){
                
                int listened_track = results.getInt(1);

			    return listened_track;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return -1;
    }

    
    @Override
    public boolean setStatus(int track_id,int status,Normal_User user){

        
        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement setStatusStatement=connection.prepareStatement("UPDATE activity SET status = ? WHERE track_id=? and user_id=?");

            setStatusStatement.setInt(1, track_id);

            setStatusStatement.setInt(2, status);

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
                
                System.out.printf("Status changed on Tracker %d to %s",track_id,status_message);

			    return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}


        return false;
    }

    @Override
    public boolean createNewTrack(user_activity activity){

        try{

			connection=ConnectionManager.getConnection();

			PreparedStatement createTrackerStatement=connection.prepareStatement("INSERT INTO activity values(?,?,?,?,?,?)");

            createTrackerStatement.setInt(1, activity.getTrack_id());

            createTrackerStatement.setInt(2, activity.getUser_id());

            createTrackerStatement.setInt(3, activity.getAlbum_id());

            createTrackerStatement.setInt(4, activity.getStatus());

            createTrackerStatement.setInt(5, activity.getListened_count());

            createTrackerStatement.setDouble(6, activity.getListened_percent());


			int results= createTrackerStatement.executeUpdate();

			if(results>-1){

                System.out.println(activity.getTrack_id() +" has been created");

			    return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }

    @Override
    public Optional<music_album> getAlbumInfo(int album_id){

        music_album album;

         try{

			connection=ConnectionManager.getConnection();

			PreparedStatement getAlbumStatement=connection.prepareStatement("SELECT * FROM album where album_id = ? ");

            getAlbumStatement.setInt(1, album_id);

			ResultSet results= getAlbumStatement.executeQuery();

			if(results.next()){
                String album_artist = results.getString(2);
                String album_name = results.getString(3);
                int album_songcount = results.getInt(4);
                int album_ratingcount = results.getInt(5);
                String album_genre= results.getString(6);
                Double album_rating = results.getDouble(7);
                LocalDate album_release = results.getDate(8).toLocalDate();

			   album=new music_album(album_id, album_artist, album_name, album_songcount, album_ratingcount, album_genre, album_rating, album_release);

			
			    return Optional.of(album);
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}



        return Optional.empty();
    }


    @Override
    public boolean addAlbum(music_album album){


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
                System.out.printf("%s has been added to the Database", album.getAlbum_name());
                return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}


        return false;
    }

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
                System.out.printf("%d has been deleted", track_id);
                return true;
            }
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}





        return false;
    }

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
                PreparedStatement setCountStatement=connection.prepareStatement("UPDATE activity SET listened = ? WHERE track_id=? and user_id=?");

                setCountStatement.setInt(1, track_id);

                setCountStatement.setInt(2, user.getId());

			    int results= setCountStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("Status changed on Tracker %d to %s",track_id,count);

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

    
    @Override
    public boolean changePassword(String password,String answer,Normal_User user){

     try{
            boolean allowed=false;

			connection=ConnectionManager.getConnection();


           PreparedStatement getAnsStatement= connection.prepareStatement("SELECT user_ans FROM user WHERE user_id = ?");

            getAnsStatement.setInt(1,user.getId());

            ResultSet securityAns= getAnsStatement.executeQuery();


            
            while (securityAns.next()) {
                if(Password_Handler.password_checker_access_ans(answer, user.getSecurityAnswer())){
                
                    allowed=true;
                }     
            }

            
            

            if(allowed){
                PreparedStatement setCountStatement=connection.prepareStatement("UPDATE user SET user_pass = ? WHERE user_id=?");

                setCountStatement.setInt(1, Password_Handler.password_worker_access(password));

                setCountStatement.setInt(2, user.getId());

			    int results= setCountStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("Password Changed");

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

    public boolean makeNormalUser(String user_name, String password,String securityAns) throws userAlreadyExists{

         try{
            
            int existing;
			connection=ConnectionManager.getConnection();


           PreparedStatement getUsernameStatement= connection.prepareStatement("SELECT COUNT(user_name) FROM user WHERE user_name= ?");



            getUsernameStatement.setString(1,user_name);

            ResultSet username= getUsernameStatement.executeQuery();

            if(username.next()){
                existing = username.getInt(1);
            }





            
            

            if(existing == 0){
                PreparedStatement createUserPreparedStatement=connection.prepareStatement("Insert INTO user Values(NULL,?,?,?,?,?);");

                createUserPreparedStatement.setString(1, user_name));

                createUserPreparedStatement.setString(2, Password_Handler.password_worker_access(securityAns));

                createUserPreparedStatement.setString(3, Password_Handler.password_worker_access(password));

                createUserPreparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

                createUserPreparedStatement.setInt(5, 0);


			    int results= createUserPreparedStatement.executeUpdate();

			    if(results>-1){

                    System.out.printf("User Created, Please Log in");

			        return true;
                }
            }else{
                throw new userAlreadyExists(user_name);
            }
			
                
            
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(userAlreadyExists e){
            e.printStackTrace();
        }catch(Exception e){
			e.printStackTrace();
		}



        return false;
    }

    public boolean makeAdminUser(String user_name, String password,String securityAns) throws userAlreadyExists{


        




        return false;
    }

    public boolean logIn(Normal_User user) throws userExceedPasswordAttempts{
        return false;
    }

    public boolean logOut(Normal_User user){
        return false;
    }

    public int numberofUsers(boolean admin){
        return -1;
    }

    public LocalDate allUsercreatedDates(boolean admin){
        return LocalDate.MIN;
    }

    public boolean renameUsername(Normal_User user){
        return false;
    }

    

}
