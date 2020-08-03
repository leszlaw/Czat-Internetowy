package pl.ostek.internet_chat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler({BlankMessageException.class, InvalidProfileException.class})
    public ResponseEntity<Object> handleBadRequestError(RuntimeException ex) {
        log.warn("Returning Http 400 Bad Request " + ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler({SuchUserExistsException.class, ProfileExistsException.class})
    public ResponseEntity<Object> handleConflictError(RuntimeException ex) {
        log.warn("Returning Http 409 Conflict " + ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler({ProfileDoesNotExistsException.class, UserNotFountException.class,
            UsernameNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundError(RuntimeException ex) {
        log.warn("Returning Http 404 Not found " + ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}