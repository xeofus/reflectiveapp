package com.xeofus.reflectiveapp.exception;

public class MethodArgumentMismatchException extends RuntimeException {

    private static final long serialVersionUID = 4555845546506077563L;

    public MethodArgumentMismatchException() {
        super();
    }

    public MethodArgumentMismatchException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public MethodArgumentMismatchException(String arg0) {
        super(arg0);
    }

    public MethodArgumentMismatchException(Throwable arg0) {
        super(arg0);
    }
}
