package raisetech.student.management2.data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StudentsCourses {



  private  String id;
  private String studentId;
  private String courseName;
  private Date courseStartAt;
  private Date courseEndAt;


}
