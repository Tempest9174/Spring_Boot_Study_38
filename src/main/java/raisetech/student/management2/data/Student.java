package raisetech.student.management2.data;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {


  private String id;
  @NotBlank(message = "名前を入力してください")
  @Size(min = 3, max = 20, message = "名前は二文字以上20字以内で入力してください")
  @Pattern(
      regexp = "^[\\p{Script=Han}]{2,20}$",
      message = "名前は漢字2字以上20字以下で入力してください")
  @NotBlank
  private String name;
  @NotBlank
  private String nickName;
  @NotBlank
  private String kanaName;
  @Email(message = "メールアドレスの形式が不正です")
  private String email;
  @NotBlank
  private String area;
  @NotNull
  private int age;
  @NotBlank
  private String sex;
  private String remark;
  private boolean isDeleted;


}
