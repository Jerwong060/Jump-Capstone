package com.jump.capstone.sql;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.jump.capstone.user.*;
import com.jump.capstone.exceptions.userAlreadyExists;
import com.jump.capstone.exceptions.userExceedPasswordAttempts;
import com.jump.capstone.music.music_album;

public interface DAOInter {

public void makeConnection() throws ClassNotFoundException,SQLException;

public void closeConnection() throws ClassNotFoundException,SQLException;

public List<user_activity> getAllActivity(int userId);

public Optional<user_activity> getActivityByTrackId(int TrackId);

public List<user_activity> getActivityByStatus(int status,int user_id);

public Double getAvgListened(int user_id);

public int getListened_count(int track_id);

public Optional<user_activity> getActivityByAlbumId(int albumId);

public boolean setStatus(int track_id);

public boolean createNewTrack(user_activity activity);

public Optional<music_album> getAlbumInfo(music_album album);

public boolean addAlbum(music_album album);

public boolean deleteTrack(int track_id,Normal_User user);

public boolean setListened_count(int track_id,Normal_user user);

public boolean deleteAlbum(int albumId,boolean admin);

public boolean changePassword(String password);

public Optional<Normal_User> makeNormalUser(String user_name, String password,String securityAns) throws userAlreadyExists;

public Optional<Admin_User> makeAdminUser(String user_name, String password,String securityAns) throws userAlreadyExists;

public boolean logIn(Normal_User user) throws userExceedPasswordAttempts;

public boolean logOut(Normal_User user); 

public int numberofUsers(boolean admin);

public LocalDate allUsercreatedDates(boolean admin);

public boolean renameUsername(Normal_User user);


}
