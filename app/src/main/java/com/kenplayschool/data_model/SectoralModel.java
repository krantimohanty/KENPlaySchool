package com.kenplayschool.data_model;

/**
 * Created by Kranti on 18/3/2016.
 */
public class SectoralModel   {

    String sector_id;
    String sector_name;
    String total_posts;

    public String getSector_id() {
        return sector_id;
    }

    public void setSector_id(String sector_id) {
        this.sector_id = sector_id;
    }

    public String getSector_name() {
        return sector_name;
    }

    public void setSector_name(String sector_name) {
        this.sector_name = sector_name;
    }

    public String getTotal_posts() {
        return total_posts;
    }

    public void setTotal_posts(String total_posts) {
        this.total_posts = total_posts;
    }


   /* @Override
    public int compareTo(SectoralModel sectoralModel) {

        return sectoralModel.sector_name.this.sector_name;
    }*/
}
