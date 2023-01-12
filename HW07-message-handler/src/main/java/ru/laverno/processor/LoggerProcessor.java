package ru.laverno.processor;

import ru.laverno.model.Message;

public class LoggerProcessor implements Processor {

    private final Processor processor;

    public LoggerProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        System.out.println("Log processing message: " + message);
        return processor.process(message);
    }
}
