package com.example.admin.runannex;
public class Phone {
    private String name;
    private int image;

    public Phone(String name, int image){
        this.name=name;
        this.image = image;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getImage() {
        return this.image;
    }
}
