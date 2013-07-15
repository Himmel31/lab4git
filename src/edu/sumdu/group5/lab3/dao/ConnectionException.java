package edu.sumdu.group5.lab3.dao;

public class ConnectionException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionException(Exception e) {
        super(e);
    }
}
