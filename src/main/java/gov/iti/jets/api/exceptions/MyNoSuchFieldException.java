package gov.iti.jets.api.exceptions;

public class MyNoSuchFieldException extends RuntimeException {
    public MyNoSuchFieldException(String msg) {
        super(msg);
    }
}
