package raisetech.student.management2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class StudentNotFoundException extends RuntimeException {

  // デフォルトコンストラクタ
  public StudentNotFoundException() {
    super("学生情報が見つかりません");
  }

  // カスタムメッセージを設定できるコンストラクタ
  public StudentNotFoundException(String message) {
    super(message);
  }

  // メッセージと原因の両方を受け取るコンストラクタ
  public StudentNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
