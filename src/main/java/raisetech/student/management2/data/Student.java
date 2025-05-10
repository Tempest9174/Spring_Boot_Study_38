package raisetech.student.management2.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
  @NotNull
  private int age;

  @Schema(description = "性別", example = "男性")
  @NotBlank
  private String sex;

  @Schema(description = "備考欄（任意）", example = "特記事項なし")
  private String remark;

  @Schema(description = "削除フラグ（true = 論理削除）", example = "false")
  private boolean isDeleted;
}
