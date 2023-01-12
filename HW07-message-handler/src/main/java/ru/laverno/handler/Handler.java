package ru.laverno.handler;

import ru.laverno.listener.Listener;
import ru.laverno.model.Message;

public interface Handler {

    Message handle(Message msg);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}
