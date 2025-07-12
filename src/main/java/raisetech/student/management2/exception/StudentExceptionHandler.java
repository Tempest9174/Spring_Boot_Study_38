package raisetech.student.management2.exception;


import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.student.management2.exception.ErrorResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(StudentExceptionHandler.class);


  /**
   * リクエストボディのバリデーションエラーを処理する
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> {
          Map<String, String> map = new HashMap<>();
          map.put("field", error.getField());
          map.put("message", error.getDefaultMessage());
          return map;
        })
        .collect(Collectors.toList());

    logger.error("バリデーションエラー発生: {}",
        errors.stream()
            .map(e -> e.get("field") + " - " + e.get("message"))
            .collect(Collectors.joining(", ")));

    Map<String, Object> response = new HashMap<>();
    response.put("message", "入力エラーが発生しました");
    response.put("errors", errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }


  /**
   * パスのパラメータの入力エラーを処理します。
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<Map<String, String>> handleMissingParameterException(
      MissingParameterException ex) {
    // ターミナルにログ出力（開発者向け）
    logger.error("パラメータの入力エラー: {}", ex.getMessage());

    // ユーザーにJSON形式でエラーメッセージを返す
    Map<String, String> response = new HashMap<>();
    response.put("error", "Validation failed: 正しい番号を入れてください。");
    response.put("message", ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  /**
   * >>×予期しないエラーを処理します。パラメータの該当学生が歯抜けな場合のエラー(例：4まで入力していて5,6が抜けていて5を検索しようとしたケース)
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */

// StudentNotFoundException をキャチしてカスタムレスポンスを返す
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
    // エラーメッセージを構築
    System.out.println("エラーメッセージを構築");
    String message = "Not Found\n" +
        "Validation failed: " +
        ex.getMessage();

    // loggerでコンソールに表示

    logger.error("パラメータが不正または存在しません: {}", ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}

//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//    String errorMessage = ex.getBindingResult().getFieldErrors()
//        .stream()
//        .map(error -> error.getField() + ": " + error.getDefaultMessage())
//        .collect(Collectors.joining(", "));
//    return ResponseEntity.badRequest().body(new ErrorResponse("バリデーションエラー: " + errorMessage));
//  }