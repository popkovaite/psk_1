package lt.vu.usecases;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.CourseDecorator;
import lt.vu.persistence.CoursesDAO;
import lt.vu.persistence.CoursesSpecDAO;
import lt.vu.persistence.FacultyDAO;
import lt.vu.entities.Course;
import lt.vu.entities.Faculty;
import lt.vu.persistence.interfaces.ICoursesDAO;

@Model
public class CourseOfFaculty implements Serializable {

    @Inject
    private FacultyDAO facultyDAO;

    @Inject
    private CoursesDAO coursesDAO;
    @Inject
    private CoursesSpecDAO coursesSpecDAO;
    @Inject
    private ICoursesDAO iCoursesDAO;


    @Getter @Setter
    private Faculty faculty;


    @Getter @Setter
    private Course courseToCreate = new Course();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer facultyId = Integer.parseInt(requestParameters.get("facultyId"));
        this.faculty = facultyDAO.findOne(facultyId);
    }
    @Transactional
    @LoggedInvocation
    public void createCourse() {
        courseToCreate.setFaculty(this.faculty);
        iCoursesDAO.persist(courseToCreate);
    }
    @Transactional
    @LoggedInvocation
    public void createCourseSpecial() {
        courseToCreate.setFaculty(this.faculty);
        coursesSpecDAO.persist(courseToCreate);
    }

}
