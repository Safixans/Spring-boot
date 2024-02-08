package uz.pdp.SpringBootDemoApplication;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.function.BiFunction;

/*@ControllerAdvice
@ResponseBody*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorDTO> error_404(ItemNotFoundException e, HttpServletRequest re){
        return ResponseEntity
                .status(404)
                .body(ErrorDTO.builder()
                        .errorPath(re.getRequestURI())
                        .errorCode(404)
                        .errorBody(e.getMessage())
                        .build());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> notValidInputExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest re){



        // specific error monitoring
        //title:notBlank shows when exception throws
        /*
        "errorBody": {
        "priority": "must not be blank",
        "title": "must not be blank"
        },
        like this
    * */
        Map<String, List<String>> errorBody=new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            errorBody.compute(field, new BiFunction<String, List<String>, List<String>>() {
                @Override
                public List<String> apply(String s, List<String> strings) {
                    strings=Objects.requireNonNullElse(strings,new ArrayList<>());
                    strings.add(defaultMessage);
                    return strings;
                }
            });
        }

        return ResponseEntity
                .status(404)
                .body(ErrorDTO.builder()
                        .errorPath(re.getRequestURI())
                        .errorCode(404)
                        .errorBody(errorBody)
                        .build());
    }
}
