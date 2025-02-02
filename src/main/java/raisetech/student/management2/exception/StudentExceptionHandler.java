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

  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<String> handleMissingParameterException(MissingParameterException ex) {
    // エラーメッセージを構築
    String message = "Bad Request\n" +
        "Validation failed: 正しい番号を入れてください。\n" +
        ex.getMessage();

    // コンソールにエラーメッセージを出力
    System.err.println("パラメータの入力エラー:" + message);

    // HTTPレスポンスとして返す
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  /**
   * 予期しないエラーを処理します。(例：受講生検索で何も入力がない場合>>正確にはスペースが入力されている場合)
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */


// StudentNotFoundException をキャッチしてカスタムレスポンスを返す
  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
    // エラーメッセージを構築
    System.out.println("エラーメッセージを構築");
    String message = "Not Found\n" +
        "Validation failed: 指定されたIDに該当する受講生が見つかりません。\n" +
        ex.getMessage();

    // ログ出力
    System.err.println("パラメータが不正または存在しません: " + ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }
}


