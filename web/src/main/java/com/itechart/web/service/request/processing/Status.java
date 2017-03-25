package com.itechart.web.service.request.processing;

/**
 * Created by Aleksandr on 20.03.2017.
 */
public enum Status {
    DELETED("DELETED"), NEW("NEW"), UPDATED("UPDATED"), NONE("NONE");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
