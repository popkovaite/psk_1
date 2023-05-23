package lt.vu.persistence;

import lt.vu.entities.Course;
import lt.vu.entities.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import lt.vu.persistence.interfaces.ICoursesDAO;

@ApplicationScoped
public class CoursesDAO implements ICoursesDAO{

    @Inject
    private EntityManager em;
    public List<Course> loadAll() {
        return em.createNamedQuery("Course.findAll", Course.class).getResultList();
    }

    public List<Course> loadAvailable(Integer id){
        List<Course> a  = em.createNamedQuery("Course.In", Course.class).setParameter("id", id).getResultList();
        //List<Course> c = em.createNamedQuery("CourseInFaculty", Course.class).setParameter("facultyId",facultyId).getResultList();
        return em.createNamedQuery("Course.findAvailabe", Course.class).setParameter("id", id).setParameter("names", a).getResultList();
    }

    public void persist(Course course){
        this.em.persist(course);
    }

    public Course findOne(Integer id){
        return em.find(Course.class, id);
    }


    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Course update(Course course){

        course = em.merge(course);
        em.flush();
        return course;
    }

}


