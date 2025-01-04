package raisetech.student.management2.data;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StudentsCourse {



  private  String id;
  private String studentId;
  private String courseName;
  private Date courseStartAt;
  private Date courseEndAt;


}
