package edu.sumdu.group5.lab4.dao;

public class DaoException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
    }

    public DaoException(Exception e) {
        super(e);
    }
}
