package ru.laverno.processor.homework;

import ru.laverno.model.Message;
import ru.laverno.processor.Processor;

public class ExceptionProcessor implements Processor {

    private final DateTimeProvider dtp;

    public ExceptionProcessor(DateTimeProvider dtp) {
        this.dtp = dtp;
    }

    @Override
    public Message process(Message message) {
        final var second = dtp.getTime().getSecond();
        if (second % 2 == 0) {
            throw new RuntimeException("Выполнение процессора отклонено! (Выполнение выпало на чётную секунду)");
        }

        return message;
    }
}
