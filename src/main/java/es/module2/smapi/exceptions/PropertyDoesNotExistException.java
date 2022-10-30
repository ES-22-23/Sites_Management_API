package es.module2.smapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PropertyDoesNotExistException extends Exception{
    private static final long serialVersionUID = 1L;

    public PropertyDoesNotExistException(String message){
        super(message);
    }
}
