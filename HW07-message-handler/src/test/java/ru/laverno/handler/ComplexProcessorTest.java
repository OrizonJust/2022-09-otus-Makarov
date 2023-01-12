package ru.laverno.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.laverno.listener.Listener;
import ru.laverno.model.Message.Builder;
import ru.laverno.processor.Processor;
import ru.laverno.processor.homework.ExceptionProcessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессора")
    void handleProcessorTest() {
        final var message = new Builder(1L).field7("field7").build();

        final var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        final var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        final var processors = List.of(processor1, processor2);

        final var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        final var result = complexProcessor.handle(message);

        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        final var message = new Builder(1L).field8("field8").build();

        final var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        final var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        final var processors = List.of(processor1, processor2);

        final var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        final var message = new Builder(1L).field9("field9").build();

        final var listener = mock(Listener.class);

        final var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        verify(listener, times(1)).onUpdate(message);
    }

    @Test
    @DisplayName("Тестирование иселючений в чётную секунду")
    void handleEvenSecondExceptionTest() {
        final var message = new Builder(1L).field2("field2").build();

        final var evenTime = LocalDateTime.of(2023,1, 12,13,7,2);
        final var oddTime = LocalDateTime.of(2023,1, 12,13,7,3);

        var processor1 = new ExceptionProcessor(() -> oddTime);
        var processor2 = new ExceptionProcessor(() -> evenTime);

        Exception exception = assertThrows(RuntimeException.class, () -> {
           processor2.process(message);
        });

        assertTrue(exception.getMessage().contains("Выполнение процессора отклонено! (Выполнение выпало на чётную секунду)"));

        assertDoesNotThrow(() -> {
            processor1.process(message);
        });

    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}
