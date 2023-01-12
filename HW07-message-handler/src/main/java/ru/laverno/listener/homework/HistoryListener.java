package ru.laverno.listener.homework;

import ru.laverno.listener.Listener;
import ru.laverno.model.Message;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class HistoryListener implements Listener, HistoryReader {

    private final Set<Message> messageSet;

    public HistoryListener() {
        this.messageSet = new HashSet<>();
    }

    @Override
    public void onUpdate(Message msg) {
        messageSet.add(new Message(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return messageSet.stream().filter(msg -> msg.getId() == id).findFirst();
    }
}
