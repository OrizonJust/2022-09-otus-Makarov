package ru.laverno.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {

    public ObjectForMessage() {}

    public ObjectForMessage(ObjectForMessage obj) {
        if (obj != null) {
            this.data = new ArrayList<>(obj.getData());
        }
    }

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{data=" + data + "}";
    }
}
