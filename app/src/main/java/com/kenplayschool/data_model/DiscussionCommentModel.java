package com.kenplayschool.data_model;

/**
 * Created by Kranti on 18/3/2016.
 */

public class DiscussionCommentModel {

    private String theme_id;
    private String comment_by;
    private String comment_id;
    private String is_like;
    private String commentor_photo;
    private String comment;
    private String date;
    private String photo;
    private String userType;

    public String getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(String theme_id) {
        this.theme_id = theme_id;
    }

    public String getComment_by() {
        return comment_by;
    }

    public void setComment_by(String comment_by) {
        this.comment_by = comment_by;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getCommentor_photo() {
        return commentor_photo;
    }

    public void setCommentor_photo(String commentor_photo) {
        this.commentor_photo = commentor_photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
