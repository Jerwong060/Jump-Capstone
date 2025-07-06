package com.jump.capstone.music;

import java.time.LocalDate;


public class music_album {
    //Object for albums mirroring the structure of data in the album table in Database

   private int album_id;
   private String album_artist;
   private String album_name;
   private int album_songcount;
   private int album_ratingcount;
   private String album_genre;
   private double album_rating;
   private LocalDate album_release;


    public music_album(int album_id,String album_artist, String album_name, int album_songcount,int album_ratingcount,String album_genre,
    double album_rating, LocalDate album_release){
        this.album_id=album_id;
        this.album_artist = album_artist;
        this.album_name=album_name;
        this.album_songcount=album_songcount;
        this.album_ratingcount=album_ratingcount;
        this.album_genre=album_genre;
        this.album_rating=album_rating;
        this.album_release=album_release;
    }

    //special constructor for newly created albums not already in the database
      public music_album(String album_artist, String album_name, int album_songcount,int album_ratingcount,String album_genre,
    double album_rating, LocalDate album_release){
        this.album_artist = album_artist;
        this.album_name=album_name;
        this.album_songcount=album_songcount;
        this.album_ratingcount=album_ratingcount;
        this.album_genre=album_genre;
        this.album_rating=album_rating;
        this.album_release=album_release;
    }

    public int getAlbum_id() {
        return album_id;
    }


    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }


    public String getAlbum_artist() {
        return album_artist;
    }


    public void setAlbum_artist(String album_artist) {
        this.album_artist = album_artist;
    }


    public String getAlbum_name() {
        return album_name;
    }


    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }


    public int getAlbum_songcount() {
        return album_songcount;
    }


    public void setAlbum_songcount(int album_songcount) {
        this.album_songcount = album_songcount;
    }


    public int getAlbum_ratingcount() {
        return album_ratingcount;
    }


    public void setAlbum_ratingcount(int album_ratingcount) {
        this.album_ratingcount = album_ratingcount;
    }


    public String getAlbum_genre() {
        return album_genre;
    }


    public void setAlbum_genre(String album_genre) {
        this.album_genre = album_genre;
    }


    public double getAlbum_rating() {
        return album_rating;
    }


    public void setAlbum_rating(double album_rating) {
        this.album_rating = album_rating;
    }


    public LocalDate getAlbum_release() {
        return album_release;
    }


    public void setAlbum_release(LocalDate album_release) {
        this.album_release = album_release;
    }


    @Override
    public String toString() {
        return "music_album [album_id=" + album_id + ", album_artist=" + album_artist + ", album_name=" + album_name
                + ", album_songcount=" + album_songcount + ", album_ratingcount=" + album_ratingcount + ", album_genre="
                + album_genre + ", album_rating=" + album_rating + ", album_release=" + album_release + "]";
    }

    //special toString for newly created albums
    public String noIdString(){
         return "music_album [ "+ "album_artist= " + album_artist + ", album_name= " + album_name
                + ", album_songcount= " + album_songcount + ", album_ratingcount= " + album_ratingcount + ", album_genre= "
                + album_genre + ", album_rating= " + album_rating + ", album_release= " + album_release + "]";
    }


}
