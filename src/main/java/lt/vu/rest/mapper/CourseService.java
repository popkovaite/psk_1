package lt.vu.rest.mapper;

import lt.vu.entities.Course;
import lt.vu.entities.Student;
import lt.vu.persistence.CoursesDAO;
import lt.vu.rest.contracts.CourseDto;
import lt.vu.rest.contracts.StudentsDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class CourseService {

    @Inject
    CoursesDAO coursesDAO;


    public CourseDto getCourseWithStudents(Course course) {

        CourseDto courseDTO = new CourseDto();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCreditNr(course.getCreditNr());

        List<StudentsDto> studentDtos = new ArrayList<>();
        for (Student student : course.getStudents()) {
            StudentsDto studentDto = new StudentsDto();
            studentDto.setName(student.getName());
            studentDto.setStudentNr(student.getStudentNr());
            studentDtos.add(studentDto);
        }

        courseDTO.setStudents(studentDtos);

        return courseDTO;
    }

}

