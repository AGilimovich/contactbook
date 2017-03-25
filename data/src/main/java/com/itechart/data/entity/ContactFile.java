package com.itechart.data.entity;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class ContactFile {
    private long id;
    private String name;

    public ContactFile() {
    }

    public ContactFile(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
