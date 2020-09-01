package pl.ostek.internet_chat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ConflictAdvice {

    @ResponseBody
    @ExceptionHandler({UserAlreadyExistsException.class, ProfileAlreadyExistsException.class,
            IncorrectContactException.class,IncorrectMessageException.class})
    public ResponseEntity<Object> handleConflictError(RuntimeException ex) {
        log.warn("Returning Http 409 Conflict " + ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
