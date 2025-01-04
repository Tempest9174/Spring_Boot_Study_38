package raisetech.student.management2.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.data.Student;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail
{
    private Student student;
    private List<StudentsCourse> studentsCourseList;

}
