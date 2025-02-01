package raisetech.student.management2.exception;

//import org.mybatis.logging.Logger;
//import org.mybatis.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.student.management2.exception.MissingParameterException;
import raisetech.student.management2.exception.StudentNotFoundException;

@RestControllerAdvice
public class StudentExceptionHandler {


  /**
   * リクエストボディのバリデーションエラーを処理する
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    // エラーメッセージ収集
    StringBuilder errorMessages = new StringBuilder("Validation failed:\n");
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errorMessages.append(String.format("%s: %s%n", error.getField(), error.getDefaultMessage()));
    }

    // コンソールにエラーメッセージを出力
    System.err.println("リクエストボディのバリデーションエラー:\n" + errorMessages);

    // エラーメッセージをPOSTMANに返す
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());
  }

  /**
   * パラメータの入力エラーを処理します。
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
  //@ExceptionHandler(ConstraintViolationException.class)
  //public ResponseEntity<String> handleConstraintViolationException(
  //    ConstraintViolationException ex) {
  //  // エラーメッセージを整形

  //  StringBuilder errorMessages = new StringBuilder("Bad Request:\n");
  //  String message = "Validation failed:入力エラーです。 " + "\n" + ex.getMessage();
  // コンソールにエラーメッセージを出力
  //  System.err.println("パラメータの入力エラー:\n" + errorMessages);
  //  return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  //}
  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<String> handleMissingParameterException(MissingParameterException ex) {
    // エラーメッセージを構築
    String message = "Bad Request:\n" +
        "Validation failed: 正しい番号を入れてください。\n" +
        ex.getMessage();

    // コンソールにエラーメッセージを出力
    System.err.println("パラメータの入力エラー:\n" + message);

    // HTTPレスポンスとして返す
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  /**
   * 予期しないエラーを処理します。(例：受講生検索で何も入力がない場合>>正確にはスペースが入力されている場合)
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
  //@ExceptionHandler(Exception.class)
  //public ResponseEntity<String> handleGenericException(Exception ex) {
  //  return new ResponseEntity<>(
  //      "An unexpected error occurred:予期しないエラー。 " + "\n" + ex.getMessage(),
  //      HttpStatus.INTERNAL_SERVER_ERROR);

// StudentNotFoundException をキャッチしてカスタムレスポンスを返す
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
    // エラーメッセージを構築
    System.out.println("エラーメッセージを構築");
    String message = "Not Found:\n" +
        "Validation failed: 番号を入れてください。\n" +
        ex.getMessage();

    // ログ出力
    System.err.println("パラメータが不正または存在しません: " + ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }
}




//registerstudentのbodyの登録時のエラー
//@ExceptionHandler(ResponseStatusException.class)
//public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
//
//POSTMANにエラー文、正しい入力をお願いしますを表示する

//  Map<String, Object> response = new HashMap<>();
//  response.put("message", ex.getReason());
//  return new ResponseEntity<>(response.toString(), ex.getStatusCode());


