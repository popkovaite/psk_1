package lt.vu.persistence;

import lt.vu.entities.Course;
import lt.vu.persistence.interfaces.ICoursesDAO;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Decorator
    public class CourseDecorator implements ICoursesDAO {
        @Inject
        @Delegate
        private ICoursesDAO iCoursesDAO;

    @Override
    public List<Course> loadAll() {

        System.out.println("Logging: load all courses started");
        List<Course> a = iCoursesDAO.loadAll();
        System.out.println("Logging: load all courses completed");
        return a;
    }
    @Override
    public List<Course> loadAvailable(Integer id){

        System.out.println("Logging: load available courses started");
        List<Course> a = iCoursesDAO.loadAvailable(id);
        System.out.println("Logging: load available courses completed");
        return a;
    }
    @Override
    public void persist(Course course){
        System.out.println("Logging: persist course started");
        iCoursesDAO.persist(course);
        System.out.println("Logging: persist course completed");
    }
    @Override
    public Course findOne(Integer id){

        System.out.println("Logging: find one course started");
        Course course = iCoursesDAO.findOne(id);
        System.out.println("Logging: find one course completed");
        return course;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Course update(Course course){
        System.out.println("Logging: Course update started");
        course = iCoursesDAO.update(course);
        System.out.println("Logging: Course update completed");
        return course;
    }

}
