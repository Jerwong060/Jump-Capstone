package com.jump.capstone.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jump.capstone.connection.ConnectionManager;
import com.jump.capstone.exceptions.userAlreadyExists;
import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.music.music_album;
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
        
        
        return allActivity;
    }

    @Override
    public Optional<user_activity> getActivityByTrackId(int TrackId){
        return Optional.empty();
    }

    @Override
    public List<user_activity> getActivityByStatus(int status,int user_id){
        List<user_activity> byStatus = new ArrayList<>();
        
        return byStatus;
    }


    @Override

    public Double getAvgListened(int user_id){
     
        
        return 000.00;
    }
    
    @Override
    public int getListened_count(int track_id){
        return -1;
    }

    @Override
    public Optional<user_activity> getActivityByAlbumId(int albumId){
        return Optional.empty();
    }

    @Override
    public boolean setStatus(int track_id){
        return false;
    }

    @Override
    public boolean createNewTrack(user_activity activity){
        return false;
    }

    public Optional<music_album> getAlbumInfo(music_album album){
        return Optional.empty();
    }


    public boolean addAlbum(music_album album){
        return false;
    }

    public boolean deleteTrack(int track_id,Normal_User user){
        return false;
    }

    public boolean setListened_count(int track_id,Normal_user user){
        return false;
    }

    public boolean deleteAlbum(int albumId,boolean admin){
        return false;
    }

    public boolean changePassword(String password){
        return false;
    }

    public Optional<Normal_User> makeNormalUser(String user_name, String password,String securityAns) throws userAlreadyExists{
        return Optional.empty();
    }

    public Optional<Admin_User> makeAdminUser(String user_name, String password,String securityAns) throws userAlreadyExists{
        return Optional.empty();
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
