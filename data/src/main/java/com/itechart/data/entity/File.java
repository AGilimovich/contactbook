package com.itechart.data.entity;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class File {
    private long id;
    private String name;
    private String storedName;

    public File() {
    }

    public File(long id, String name, String storedName) {
        this.id = id;
        this.name = name;
        this.storedName = storedName;
    }

    public void update(File file) {
        this.name = file.getName();
        // TODO: 27.03.2017 use common library for check
        if (file.getStoredName() != null)
            this.storedName = file.getStoredName();
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

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }
}
