package raisetech.student.management2.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management2.data.StudentsCourse;
import raisetech.student.management2.data.Student;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDetail
{
    @Valid
    private Student student;
    private List<StudentsCourse> studentsCourseList;

}
