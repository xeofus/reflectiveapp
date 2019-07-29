package com.xeofus.reflectiveapp.exception;

public class MethodNotImplementedException extends RuntimeException {

    private static final long serialVersionUID = 6059799778601348363L;

    public MethodNotImplementedException() {
        super();
    }

    public MethodNotImplementedException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public MethodNotImplementedException(String arg0) {
        super(arg0);
    }

    public MethodNotImplementedException(Throwable arg0) {
        super(arg0);
    }
}
