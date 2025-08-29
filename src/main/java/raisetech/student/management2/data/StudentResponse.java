package raisetech.student.management2.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * フロントエンドへ返却する受講生情報のDTO
 */
@Data
@AllArgsConstructor
public class StudentResponse {
  private String id;
  private String name;
  private String kanaName;
  private String nickName;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark;
  private boolean isDeleted;
  private List<String> courses;
}