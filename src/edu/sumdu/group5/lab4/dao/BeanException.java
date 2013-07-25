package edu.sumdu.group5.lab4.dao;

public class BeanException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Exception e) {
        super(message, e);
    }

    public BeanException(Exception e) {
        super(e);
    }
}
