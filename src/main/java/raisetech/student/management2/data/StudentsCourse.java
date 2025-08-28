package raisetech.student.management2.data;

import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "受講生のコース情報")
public class StudentsCourse {

  @Schema(description = "コースID", example = "10")
  private String id;

  @Schema(description = "受講生ID", example = "3")
  private String studentId;

  @Schema(description = "コース名", example = "Java基礎講座")
  @NotNull(message = "courseName は必須です")
  private String courseName;

  @Schema(description = "コース開始日", example = "2024-04-01")
  private Date courseStartAt;

  @Schema(description = "コース終了日", example = "2024-06-30")
  private Date courseEndAt;
}
