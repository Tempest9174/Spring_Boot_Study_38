package raisetech.student.management2.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * コース一覧APIのレスポンスDTO
 */
@Data
@AllArgsConstructor
public class CourseResponse {
  private String id;
  private String name;
  private int students;
}