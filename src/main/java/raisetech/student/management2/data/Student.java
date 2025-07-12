package raisetech.student.management2.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "受講生の基本情報")
public class Student {

  @Schema(description = "受講生ID", example = "123")

  private String id;

  @Schema(description = "氏名（最大10文字）", example = "山田太郎")
  @NotBlank(message = "名前を入力してください")
  @Size(max = 10, message = "名前は10字以内で入力してください")
  private String name;

  @Schema(description = "ニックネーム（任意）", example = "たろちゃん")
  private String nickName;

  @Schema(description = "ふりがな", example = "やまだたろう")
  private String kanaName;

  @Schema(description = "メールアドレス", example = "taro@example.com")
  @Email(message = "メールアドレスの形式が不正です")
  @NotBlank
  private String email;

  @Schema(description = "居住地域", example = "東京")
  @NotBlank
  private String area;

  @Schema(description = "年齢", example = "25")
  @NotNull(message = "年齢を数値で入力してください")
  @Min(value = 1, message = "年齢は1以上を入力してください")
  private int age;

  @Schema(description = "性別", example = "男性")
  @NotBlank
  private String sex;

  @Schema(description = "備考欄（任意）", example = "特記事項なし")
  private String remark;

  @Schema(description = "削除フラグ（true = 論理削除）", example = "false")
  private boolean isDeleted;

  //public Student(String number, String 佐藤太郎, String さとうたろう, String タロ, String mail,
  //    String 東京, int i, String 男性, String s,
  //    boolean b) {

  //}
}
