package com.kenplayschool.data_model;

/**
 * Created by Kranti on 18/3/2016.
 */
public class CommentModel {

    String comment_id;
    String date;
    String comment;
    String comment_by;
    String commentor_photo;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_by() {
        return comment_by;
    }

    public void setComment_by(String comment_by) {
        this.comment_by = comment_by;
    }

    public String getCommentor_photo() {
        return commentor_photo;
    }

    public void setCommentor_photo(String commentor_photo) {
        this.commentor_photo = commentor_photo;
    }
}
