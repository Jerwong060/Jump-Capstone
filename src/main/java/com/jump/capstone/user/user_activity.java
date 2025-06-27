package com.jump.capstone.user;

public class user_activity {


    private int track_id;
    private int user_id;
    private int album_id;
    private int status;
    private int listened_count;
    private double listened_percent;
    
    public user_activity(int track_id, int user_id, int album_id, int status, int listened_count,
            double listened_percent) {
        this.track_id = track_id;
        this.user_id = user_id;
        this.album_id = album_id;
        this.status = status;
        this.listened_count = listened_count;
        this.listened_percent = listened_percent;
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

    public double getListened_percent() {
        return listened_percent;
    }

    public void setListened_percent(double listened_percent) {
        this.listened_percent = listened_percent;
    }


    public int getUser_id() {
        return user_id;
    }


    

}
