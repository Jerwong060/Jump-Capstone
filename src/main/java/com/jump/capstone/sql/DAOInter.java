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

public Optional<user_activity> getActivityByTrackId(int TrackId,int user_id);

public List<user_activity> getActivityByStatus(int status,int user_id);

public Double getAvgListened(int user_id);

public int getListened_count(int track_id,int user_id);

public boolean setStatus(int track_id,int status,Normal_User user);

public boolean createNewTrack(user_activity activity);

public Optional<music_album> getAlbumInfo(int album_id);

public boolean addAlbum(music_album album,boolean admin_access);

public List<music_album> listAllalbums();

public Optional<music_album> getAlbumByid(int album_id);

public boolean deleteTrack(int track_id,Normal_User user);

public boolean giveRating(int album_id,Double rating);

public boolean setListened_count(int track_id,int count,Normal_User user);

public boolean changePassword(String password,String answer,Normal_User user);

public boolean makeNormalUser(String user_name, String password,String securityAns) throws userAlreadyExists;

public boolean makeAdminUser(String user_name, String password,String securityAns,String admString) throws userAlreadyExists;

public Optional<Normal_User> logIn(String username,String password) throws userExceedPasswordAttempts;

public int numberofUsers(boolean admin);

public List<LocalDate> allUsercreatedDates(boolean admin);

public boolean renameUsername(Normal_User user,String userName);


}
