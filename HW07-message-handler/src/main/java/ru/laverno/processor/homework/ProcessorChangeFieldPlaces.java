package ru.laverno.processor.homework;

import ru.laverno.model.Message;
import ru.laverno.processor.Processor;

public class ProcessorChangeFieldPlaces implements Processor {

    @Override
    public Message process(Message message) {
        final var prevField11 = message.getField11();
        final var prevField12 = message.getField12();
        return message.toBuilder().field11(prevField12).field12(prevField11).build();
    }
}
