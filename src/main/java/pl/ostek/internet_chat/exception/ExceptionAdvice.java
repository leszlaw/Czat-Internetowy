package pl.ostek.internet_chat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BlankMessageException.class)
    public ResponseEntity<Object> handleBlankMessageException(BlankMessageException ex){
        log.warn("Returning Http 400 Bad Request "+ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(SuchUserExistsException.class)
    public ResponseEntity<Object> handleSuchUserExistsException(SuchUserExistsException ex){
        log.warn("Returning Http 409 Conflict "+ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ExceptionHandler(ProfileExistsException.class)
    public ResponseEntity<Object> handleProfileExistsException(ProfileExistsException ex){
        log.warn("Returning Http 409 Conflict "+ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
