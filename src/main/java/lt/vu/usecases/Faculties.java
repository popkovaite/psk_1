package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Course;
import lt.vu.persistence.FacultyDAO;
import lt.vu.entities.Faculty;
import lt.vu.persistence.interfaces.ICoursesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Faculties {

    @Inject
    private FacultyDAO facultyDAO;
    @Inject
    private ICoursesDAO IcoursesDAO;

    @Getter @Setter
    private Faculty facultyToCreate = new Faculty();

    @Getter
    private List<Faculty> allFaculties;
    @Getter
    private List<Course> popCourses;

    @PostConstruct
    public void init(){
        loadAllFaculties();
        loadCourse();
    }

    @Transactional
    public void createFaculty(){
        this.facultyDAO.persist(facultyToCreate);
    }

    private void loadAllFaculties(){
        this.allFaculties = facultyDAO.loadAll();
    }
    private void loadCourse(){
        popCourses = IcoursesDAO.loadAll();
    }
}
