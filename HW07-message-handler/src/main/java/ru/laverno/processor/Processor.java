package ru.laverno.processor;

import ru.laverno.model.Message;

public interface Processor {

    Message process(Message message);
}
