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
public class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler({ProfileDoesNotExistsException.class, UserNotFoundException.class,
            UsernameNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundError(RuntimeException ex) {
        log.warn("Returning Http 404 Not found " + ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
