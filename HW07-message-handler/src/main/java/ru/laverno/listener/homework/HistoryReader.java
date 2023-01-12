package ru.laverno.listener.homework;

import ru.laverno.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
