package com.alex.gamecrush.juegozombie;

public class ListElement {
    public String color;
    public String name;
    public String score;


    public ListElement(String color, String name, String score) {
        this.color = color;
        this.name = name;
        this.score = score;
    }

    public ListElement(String name, String score) {
        this.name = name;
        this.score = score;
        this.color = "#FFFFFF";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
