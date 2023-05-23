package lt.vu.rest.mapper;

import lt.vu.entities.Course;
import lt.vu.entities.Student;
import lt.vu.persistence.CoursesDAO;
import lt.vu.persistence.StudentsDAO;
import lt.vu.rest.contracts.CourseDto;
import lt.vu.rest.contracts.StudentsDto;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

        @Inject
       StudentsDAO studentsDAO;

        public StudentsDto getCourseWithStudents(Student student) {
            StudentsDto studentDto = new StudentsDto();
            studentDto.setName(student.getName());
            studentDto.setStudentNr(student.getStudentNr());
            studentDto.setId(studentDto.getId());


        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : student.getCourses()) {
            CourseDto courseDTO = new CourseDto();
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            courseDTO.setCreditNr(course.getCreditNr());
            courseDtos.add(courseDTO);
        }

            studentDto.setCourses(courseDtos);

        return studentDto;
    }

    }


