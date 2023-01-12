package ru.laverno.handler;

import ru.laverno.listener.Listener;
import ru.laverno.model.Message;
import ru.laverno.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComplexProcessor implements Handler {

    private final List<Listener> listeners = new ArrayList<>();
    private final List<Processor> processors;
    private final Consumer<Exception> errorHandler;

    public ComplexProcessor(List<Processor> processors, Consumer<Exception> errorHandler) {
        this.processors = processors;
        this.errorHandler = errorHandler;
    }
    
    @Override
    public Message handle(final Message msg) {
        var newMsg = msg;

        for (var pros : processors) {
            try {
                newMsg = pros.process(newMsg);
            } catch (Exception ex) {
                ex.printStackTrace();
                errorHandler.accept(ex);
            }
        }
        notify(newMsg);
        return newMsg;
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notify(Message msg) {
        listeners.forEach(listener -> {
            try {
                listener.onUpdate(msg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
