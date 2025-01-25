package raisetech.student.management2.exception;



import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
    // エラーメッセージを整形
    String message = "Validation failed:入力エラーです。 " + ex.getMessage();
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return new ResponseEntity<>("An unexpected error occurred:予期しないエラー。 " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  //registerstudentのbodyの登録時のエラー
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
    //POSTMANにエラー文、正しい入力をお願いしますを表示する
    Map<String, Object> response = new HashMap<>();
    response.put("message", ex.getReason());
    return new ResponseEntity<>(response.toString(), ex.getStatusCode());
  }
}
