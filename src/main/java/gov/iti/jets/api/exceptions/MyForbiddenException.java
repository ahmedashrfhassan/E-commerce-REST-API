package gov.iti.jets.api.exceptions;

public class MyForbiddenException extends RuntimeException{
    public MyForbiddenException(String msg){
        super(msg);
    }
}
