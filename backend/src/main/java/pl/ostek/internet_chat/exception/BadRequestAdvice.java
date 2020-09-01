package pl.ostek.internet_chat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class BadRequestAdvice {

    @ResponseBody
    @ExceptionHandler({BlankMessageException.class, InvalidProfileException.class, IncorrectUserException.class})
    public ResponseEntity<Object> handleBadRequestError(RuntimeException ex) {
        log.warn("Returning Http 400 Bad Request " + ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}