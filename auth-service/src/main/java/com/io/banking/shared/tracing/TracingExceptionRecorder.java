package com.io.banking.shared.tracing;

import com.io.banking.shared.exception.BankingException;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TracingExceptionRecorder {

    public void recordHandledBusinessException(BankingException exception, HttpStatus status) {
        Span span = Span.current();
        if (!span.getSpanContext().isValid()) {
            return;
        }

        span.recordException(exception);
        span.setAttribute("application.error.type", exception.getClass().getSimpleName());
        if (status.is5xxServerError()) {
            span.setStatus(StatusCode.ERROR, exception.getMessage());
        }
    }
}
