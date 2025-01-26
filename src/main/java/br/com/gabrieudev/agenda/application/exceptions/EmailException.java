package br.com.gabrieudev.agenda.application.exceptions;

import java.time.LocalDateTime;

public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }

    public StandardException toStandardException() {
        return new StandardException(500, this.getMessage(), LocalDateTime.now());
    }
}
