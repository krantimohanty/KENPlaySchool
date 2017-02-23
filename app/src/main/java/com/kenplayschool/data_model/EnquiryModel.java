package com.kenplayschool.data_model;

/**
 * Created by Kranti on 18/3/2016.
 */

public class EnquiryModel {

    String EnquiryNo;
    String Name;
    String Queries;
    String  PhoneNo;
    String  EmailId;
    String  Area;
    String  Source;
    String profilePic;
    String EnquiryType;

    public String getEnquiryNo() {
        return EnquiryNo;
    }

    public void setEnquiryNo(String enquiryNo) {
        EnquiryNo = enquiryNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQueries() {
        return Queries;
    }

    public void setQueries(String queries) {
        Queries = queries;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEnquiryType() {
        return EnquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        EnquiryType = enquiryType;
    }
}
