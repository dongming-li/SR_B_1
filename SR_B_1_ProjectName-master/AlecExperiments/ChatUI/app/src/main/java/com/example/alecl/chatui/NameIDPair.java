package com.example.alecl.chatui;

/**
 * Created by alecl on 12/2/2017.
 */

public class NameIDPair {
    private String name;
    private int id;
    public NameIDPair(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public int getID(){
        return id;
    }
}
