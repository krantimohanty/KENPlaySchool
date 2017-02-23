package com.kenplayschool.data_model;

/**
 * Created by Kranti on 18/3/2016.
 */
public class PhotoModel {

    String album_id;
    String album_photo;
    String title;
    String date;
    String photo_count;
    String id;
    String url;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(String photo_count) {
        this.photo_count = photo_count;
    }

    public String getalbum_id() {
        return album_id;
    }
    public String setAlbum_id() {
        return album_id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getalbum_photo() {
        return album_photo;
    }

    public void setAlbum_photo(String album_photo) {
        this.album_photo = album_photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
