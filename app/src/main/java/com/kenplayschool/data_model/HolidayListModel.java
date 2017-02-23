package com.kenplayschool.data_model;

/**
 * Created by suchismita.p on 5/5/2016.
 */
public class HolidayListModel {
    String date,day,holiday,month;
    public HolidayListModel() {
    }

    public HolidayListModel(String date, String day, String holiday, String month) {
        this.date = date;
        this.day = day;
        this.holiday = holiday;
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
