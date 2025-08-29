package raisetech.student.management2.data;

import java.util.List;
import lombok.Data;

/**
 * 受講生情報の登録・更新で利用するリクエストDTO
 */
@Data
public class StudentRequest {
  private String name;
  private String kanaName;
  private String nickName;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark;
  private List<String> courses;
}