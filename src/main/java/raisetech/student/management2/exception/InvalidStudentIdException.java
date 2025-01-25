package raisetech.student.management2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
//不正な学生IDが指定された場合の例外クラス


public class InvalidStudentIdException extends RuntimeException {
  public InvalidStudentIdException(String message) {
    super(message);
  }




}
