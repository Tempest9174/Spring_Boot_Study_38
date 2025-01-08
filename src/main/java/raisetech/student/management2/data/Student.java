package raisetech.student.management2.data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {
  @NotNull
  @Size(min = 1, max = 2, message = "IDは1文字以上2文字以下で入力してください")
  private String id;
  @NotNull(message = "名前を入力してください")
  @Size(min = 3, max = 20, message = "名前は二文字以上20字以内で入力してください")
  @Pattern(
      regexp = "^[\\p{Script=Han}]{2,20}$",
      message = "名前は漢字2字以上20字以下で入力してください")
  @NotNull
  private String name;
  @NotNull
  private String nickName;
  @NotNull
  private String kanaName;
  @Email(message = "メールアドレスの形式が不正です")
  private String email;
  @NotNull
  private String area;
  @NotNull
  private int age;
  @NotNull
  private String sex;
  private String remark;
  private boolean isDeleted;


}
