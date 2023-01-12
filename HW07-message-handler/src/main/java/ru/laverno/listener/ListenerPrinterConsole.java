package ru.laverno.listener;

import ru.laverno.model.Message;

public class ListenerPrinterConsole implements Listener {

    @Override
    public void onUpdate(Message msg) {
        final var logString = String.format("oldMsg:%s", msg);
        System.out.println(logString);
    }
}
