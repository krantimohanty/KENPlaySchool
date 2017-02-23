package com.kenplayschool.data_model;

/**
 * Created by Kranti on 22/3/2016.
 */

public class BulletinModel {

    String BulletinTitle;
    String BulletinComments;
    String  StartDate;
    String  BulletinImage;

    public String getBulletinImage() {
        return BulletinImage;
    }

    public void setBulletinImage(String bulletinImage) {
        BulletinImage = bulletinImage;
    }

    public String getBulletinTitle() {
        return BulletinTitle;
    }

    public void setBulletinTitle(String bulletinTitle) {
        BulletinTitle = bulletinTitle;
    }

    public String getBulletinComments() {
        return BulletinComments;
    }

    public void setBulletinComments(String bulletinComments) {
        BulletinComments = bulletinComments;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
}
