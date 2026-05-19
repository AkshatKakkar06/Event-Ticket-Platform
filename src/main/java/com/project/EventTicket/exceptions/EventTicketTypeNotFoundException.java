package com.project.EventTicket.exceptions;

public class EventTicketTypeNotFoundException extends EventTicketException{
    public EventTicketTypeNotFoundException() {
    }

    public EventTicketTypeNotFoundException(String message) {
        super(message);
    }

    public EventTicketTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventTicketTypeNotFoundException(Throwable cause) {
        super(cause);
    }

    public EventTicketTypeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

