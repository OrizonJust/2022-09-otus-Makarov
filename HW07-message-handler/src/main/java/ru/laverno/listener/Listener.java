package ru.laverno.listener;

import ru.laverno.model.Message;

public interface Listener {

    void onUpdate(Message msg);
}
