package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Course;
import lt.vu.entities.Student;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class CourseDto {

    private Integer id;
    private String Name;

    private Integer CreditNr;

    private String FacultyName;
    private List<StudentsDto> Students;

}
