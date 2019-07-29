package com.xeofus.reflectiveapp.exception;

public class DispatchException extends RuntimeException {

    private static final long serialVersionUID = 6331939092820452709L;

    public DispatchException() {
        super();
    }

    public DispatchException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DispatchException(String arg0) {
        super(arg0);
    }

    public DispatchException(Throwable arg0) {
        super(arg0);
    }
}
