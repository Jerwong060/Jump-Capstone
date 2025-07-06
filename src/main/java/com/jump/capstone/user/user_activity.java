package com.jump.capstone.user;

import com.jump.capstone.sql.*;

public class user_activity {


    private int track_id=1;
    private int user_id;
    private int album_id;
    private int status;
    private int listened_count;
    private int total_songs;
    
    public user_activity(int user_id, int album_id, int status, int listened_count,
        int total_songs) {
        this.track_id = track_id++;
        this.user_id = user_id;
        this.album_id = album_id;
        this.status = status;
        this.listened_count = listened_count;
        this.total_songs=total_songs;
    }


     public user_activity(int tracker_id,int user_id, int album_id, int status, int listened_count,
        int total_songs) {
        this.track_id = tracker_id;
        this.user_id = user_id;
        this.album_id = album_id;
        this.status = status;
        this.listened_count = listened_count;
        this.total_songs=total_songs;
    }


    public int getTrack_id() {
        return track_id;
    }

    public void setTrack_id(int track_id) {
        this.track_id = track_id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getListened_count() {
        return listened_count;
    }

    public void setListened_count(int listened_count) {
        this.listened_count = listened_count;
    }

    public int getTotal_songs() {
        return  total_songs;
    }

    public void setTotal_songs(int total_songs) {
        this.total_songs = total_songs;
    }


    public int getUser_id() {
        return user_id;
    }

    
    public String toString(DAOInter info) {
        String username =  info.getAccountUser(user_id);
        String album_name= info.getAlbumByid(album_id).get().getAlbum_name();
        double percent_listened = ((double)listened_count/(double)total_songs)*100;

        return "track_id=" + track_id + ", Owner=" + username + ", album_id=" + album_id + ", Album Name: " + album_name +", status="
                + status + ", listened_count=" + listened_count + ", total_songs=" + total_songs + ", Percent Listened: "+ String.format("%.2f", percent_listened)
                 + "\n";
    }

    public String noIdString(DAOInter info){
        String username =  info.getAccountUser(user_id);
        String album_name= info.getAlbumByid(album_id).get().getAlbum_name();
        double percent_listened = ((double)listened_count/(double)total_songs)*100;

        return  "Owner=" + username + ", album_id=" + album_id + ", Album Name: " + album_name +", status="
                + status + ", listened_count=" + listened_count + ", total_songs=" + total_songs + ", Percent Listened: "+ String.format("%.2f", percent_listened)
                 + "\n";

    }

    

}
