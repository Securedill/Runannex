package com.example.admin.runannex;

import java.util.Date;

public class Phone1 {
    private String date;
    private int imageMap;
    private String info;

    public Phone1(String date, int imageMap, String info){
        this.date=date;
        this.info=info;
        this.imageMap = imageMap;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getInfo() {

        return this.info;
    }
    public void setInfo(String info) {

        this.date = info;
    }
    public int getImageMap() {
        return this.imageMap;
    }
}
