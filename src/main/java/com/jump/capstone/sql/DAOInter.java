package com.jump.capstone.sql;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.jump.capstone.user.*;
import com.jump.capstone.music.music_album;

public interface DAOInter {

public void makeConnection() throws ClassNotFoundException,SQLException;

public void closeConnection() throws ClassNotFoundException,SQLException;

public List<user_activity> getAllActivity(int userId);

public List<user_activity> getActivityByUserId(int userId);

public Optional<user_activity> getActivityByTrackId(int TrackId);

public List<user_activity> getActivityByStatus(int status);

public Double getAvgListened(int track_id);

public int getListened_count(int track_id);

public Optional<user_activity> getActivityByAlbumId(int albumId);

public boolean setStatus(int track_id);

public boolean createNewTrack(user_activity activity);

public Optional<music_album> getAlbumInfo(music_album album);

public boolean addAlbum(music_album album, boolean admin);

public boolean deleteTrack(int track_id);

public boolean setListened_count(int track_id);

public boolean deleteAlbum(int albumId,boolean admin);

public boolean changePassword(String password);


}
