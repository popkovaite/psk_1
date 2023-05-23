package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Course;
import lt.vu.entities.Faculty;
import lt.vu.entities.Student;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.CoursesDAO;
import lt.vu.persistence.FacultyDAO;
import lt.vu.persistence.StudentsDAO;
import lt.vu.services.LongCalculationComponent;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@Model
public class StudentsInCourse implements Serializable {
    @Inject
    private StudentsDAO studentDAO;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CoursesDAO courseDAO;
    @Getter @Setter
    private Course course;
    @Getter
    private List<Student> availableStudents;
    @Getter @Setter
    private List<Faculty> faculties;
    @Getter @Setter
    private Student studentToAdd = new Student();
    @Getter @Setter
    private Student chosenStudent = new Student();


    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer courseId = Integer.parseInt(requestParameters.get("courseId"));
        this.course = courseDAO.findOne(courseId);
        loadAvailable(courseId);
    }

    private void loadAvailable(Integer id){
        this.availableStudents = studentDAO.loadAvailable(id);
    }

    private void faculties(Student stud){
        this.faculties = studentDAO.faculties(stud);
    }
    @Transactional
    @LoggedInvocation
    public void addStudent() {
        List<Course> courses = new ArrayList<>();
        courses.add(this.course);
        studentToAdd.setCourses(courses);
        studentDAO.persist(this.studentToAdd);
    }
    @Transactional
    @LoggedInvocation
    public void chooseStudent() {
        this.chosenStudent = studentDAO.findOne(chosenStudent.getId());
        course.getStudents().add(chosenStudent);
        chosenStudent.getCourses().add(course);
        studentDAO.update(this.chosenStudent);

    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @LoggedInvocation
    public String optimisticlock(){
        AtomicReference<String> returnSt = new AtomicReference<>(new String());

        try{
            Thread thread1 = new Thread(() -> {
                try {
                    course.setCreditNr(15);
                    update(course);
                }
                catch (javax.persistence.OptimisticLockException o) {
                    System.out.println(o);
                    System.out.println("1");
                    returnSt.set("/courseDetails?faces-redirect=true&amp;courseId=" + this.course.getId() + "&error=optimistic-lock-exception");
                }
            });

            Thread thread2 = new Thread(() -> {
                try{
                course.setCreditNr(20);
                update(course);
                }
                catch (javax.persistence.OptimisticLockException o) {
                    System.out.println(o);
                    System.out.println("2");
                    returnSt.set("/courseDetails?faces-redirect=true&amp;courseId=" + this.course.getId() + "&error=optimistic-lock-exception");
                }
            });
            thread1.start();
            thread1.sleep(2000);
            thread2.start();
            thread1.join();
            thread2.join();
        }
        catch (Exception o) {
            System.out.println(o);
        }
        return returnSt.get();
    }
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Course update(Course course){

        course = em.merge(course);
        em.flush();
        return course;
    }
}
