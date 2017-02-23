package com.hani.myfood.model;

/**
 *
 */

public class Recipe {
    private int Id;
    String title;
    boolean isVeg;
    String imagePath1;
    String imagePath2;
    String imagePath3;

    public Recipe() {

    }

    public Recipe(int id, String title, boolean isVeg, String imagePath1, String imagePath2, String imagePath3) {
        Id = id;
        this.title = title;
        this.isVeg = isVeg;
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = imagePath3;
    }

    public Recipe(String title, boolean isVeg, String imagePath1, String imagePath2, String imagePath3) {
        this.title = title;
        this.isVeg = isVeg;
        this.imagePath1 = imagePath1;
        this.imagePath2 = imagePath2;
        this.imagePath3 = imagePath3;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public void setImagePath1(String imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public void setImagePath2(String imagePath2) {
        this.imagePath2 = imagePath2;
    }

    public void setImagePath3(String imagePath3) {
        this.imagePath3 = imagePath3;
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public String getImagePath1() {
        return imagePath1;
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public String getImagePath3() {
        return imagePath3;
    }
}
