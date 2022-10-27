package es.module2.smapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlarmAlreadyExistsException extends Exception{
    private static final long serialVersionUID = 1L;

    public AlarmAlreadyExistsException(String message){
        super(message);
    }
}
