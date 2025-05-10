package raisetech.student.management2.exception;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {

  //private static final Logger LOGGER = Logger.getLogger(StudentExceptionHandler.class.getName());
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
    // ユーザー向けのエラーメッセージをMapで整理
    List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> Map.of("field", error.getField(), "message", error.getDefaultMessage()))
        .collect(Collectors.toList());



    // ログ出力（SLF4J）
    logger.error("バリデーションエラー発生: {}",
        errors.stream().map(e -> e.get("field") + " - " + e.get("message")).collect(Collectors.joining(", ")));

    // ユーザー向けレスポンス
    Map<String, Object> response = Map.of(
        "message", "入力エラーが発生しました",
        "errors", errors
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }


  /**
   * パラメータの入力エラーを処理します。
   *
   * @param ex
   * @return HTTPステータスコード と、改行で区切られたバリデーションエラーメッセージ
   */
  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<Map<String, String>> handleMissingParameterException(MissingParameterException ex) {
    // ターミナルにログ出力（開発者向け）
    logger.error("パラメータの入力エラー: {}", ex.getMessage());

    // ユーザーにJSON形式でエラーメッセージを返す
    Map<String, String> response = new HashMap<>();
    response.put("error", "Validation failed: 正しい番号を入れてください。");
    response.put("message", ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
        "Validation failed: " +
        ex.getMessage();

    // loggerでコンソールに表示

    logger.error("パラメータが不正または存在しません: {}", ex.getMessage());

    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
  }
}


