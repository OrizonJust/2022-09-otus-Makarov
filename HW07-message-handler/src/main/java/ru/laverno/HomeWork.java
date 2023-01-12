package ru.laverno;

import ru.laverno.handler.ComplexProcessor;
import ru.laverno.listener.ListenerPrinterConsole;
import ru.laverno.model.Message.Builder;
import ru.laverno.model.ObjectForMessage;
import ru.laverno.processor.homework.ProcessorChangeFieldPlaces;
import ru.laverno.processor.homework.ExceptionProcessor;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    public static void main(String[] args) {
        try {
            final var processors = List.of(
                    new ProcessorChangeFieldPlaces(),
                    new ExceptionProcessor(LocalDateTime::now));

            final var complexProcessor = new ComplexProcessor(processors, ex -> {
            });

            final var listenerPrinter = new ListenerPrinterConsole();
            complexProcessor.addListener(listenerPrinter);

            final var field11 = new ObjectForMessage();
            final var field12 = new ObjectForMessage();
            field11.setData(List.of("field11"));
            field12.setData(List.of("field12"));
            final var message = new Builder(1L).field11(field11).field12(field12).build();

            final var result = complexProcessor.handle(message);
            System.out.println("result:" + result);

            complexProcessor.removeListener(listenerPrinter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
