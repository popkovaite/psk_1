package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Course;
import lt.vu.entities.Student;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.CoursesDAO;
import lt.vu.persistence.StudentsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Model
public class CoursesOfStudent {

        @Inject
        private StudentsDAO studentDAO;
        @Inject
        private CoursesDAO courseDAO;

        @Getter
        private List<Course> availableCourse;

        @Getter @Setter
        private Course chosenCourse = new Course();
        @Getter @Setter
        private Course chosenCourse1 = new Course();
        @Getter @Setter
        private Student student;
        @PostConstruct
        public void init() {
            Map<String, String> requestParameters =
                    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer studentId = Integer.parseInt(requestParameters.get("studentId"));
            this.student = studentDAO.findOne(studentId);
            loadAvailable(studentId);
        }

        private void loadAvailable(Integer id){
            this.availableCourse = courseDAO.loadAvailable(id);
        }
        @Transactional
        @LoggedInvocation
        public void chooseCourse()  {
                this.chosenCourse = courseDAO.findOne(chosenCourse.getId());
                student.getCourses().add(chosenCourse);
                chosenCourse.getStudents().add(student);
                courseDAO.update(this.chosenCourse);

        }
    }


