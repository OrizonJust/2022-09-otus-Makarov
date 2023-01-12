package ru.laverno.listener;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.laverno.listener.homework.HistoryListener;
import ru.laverno.model.Message.Builder;
import ru.laverno.model.ObjectForMessage;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class HistoryListenerTest {

    @Test
    void listenerTest() {
        final var historyListener = new HistoryListener();

        final var id = 100L;
        final var data = "33";
        final var field13 = new ObjectForMessage();
        final var field13Data = new ArrayList<String>();

        field13Data.add(data);
        field13.setData(field13Data);

        var message = new Builder(id)
                .field10("field10")
                .field13(field13)
                .build();

        historyListener.onUpdate(message);
        message.getField13().setData(new ArrayList<>());
        field13Data.clear();

        final var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
        assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
    }
}
