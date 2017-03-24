package com.itechart.web.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandr on 24.03.2017.
 */
public enum CommandType {

    ROOT("/"), EMAIL("/email"), SEND("/send"), SEARCH("/search"),
    ADD("/add"), EDIT("/edit"), SAVE("/save"), UPDATE("/update"),
    FIND("/find"), DELETE("/delete"), FILE("/file");

    private String path;
    private static final Map<String, CommandType> stringToEnum = new HashMap<>();

    static {
        for (CommandType command : values()) {
            stringToEnum.put(command.toString(), command);
        }
    }

    CommandType(String path) {
        this.path = path;
    }

    public String toString() {
        return path;
    }

    public static CommandType fromString(String path) {
        return stringToEnum.get(path);
    }


}
