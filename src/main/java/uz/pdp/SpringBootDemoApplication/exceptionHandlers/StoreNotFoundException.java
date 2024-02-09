package uz.pdp.SpringBootDemoApplication.exceptionHandlers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StoreNotFoundException extends RuntimeException{

    public StoreNotFoundException(String message) {
        super(message);
    }
}
