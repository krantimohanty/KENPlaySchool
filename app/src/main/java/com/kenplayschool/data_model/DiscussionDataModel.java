package com.kenplayschool.data_model;

/**
 * Created by Kranti on 18/3/2016.
 */

public class DiscussionDataModel {

     private  String theme_id;
     private  String thumnail_photo;
     private  String posted_on;
     private  String like_count;
     private  String is_like;
     private  String replies_count;
     private  String last_message_on;
     private  String theme_title;
     private  String last_message_by;
     private  String view_count;

    public String getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
    }

    public String getThumnail_photo() {
        return thumnail_photo;
    }

    public void setThumnail_photo(String thumnail_photo) {
        this.thumnail_photo = thumnail_photo;
    }

    public String getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(String posted_on) {
        this.posted_on = posted_on;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getReplies_count() {
        return replies_count;
    }

    public void setReplies_count(String replies_count) {
        this.replies_count = replies_count;
    }

    public String getLast_message_on() {
        return last_message_on;
    }

    public void setLast_message_on(String last_message_on) {
        this.last_message_on = last_message_on;
    }

    public String getTheme_title() {
        return theme_title;
    }

    public void setTheme_title(String theme_title) {
        this.theme_title = theme_title;
    }

    public String getLast_message_by() {
        return last_message_by;
    }

    public void setLast_message_by(String last_message_by) {
        this.last_message_by = last_message_by;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }
}
