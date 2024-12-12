package raisetech.student.management2.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management2.data.StudentsCourses;
import raisetech.student.management2.data.Student;

@Getter
@Setter
public class StudentDetail
{
    private Student student;
    private List<StudentsCourses> studentsCourses;

}
