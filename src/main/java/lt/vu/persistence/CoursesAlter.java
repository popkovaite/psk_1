package lt.vu.persistence;

import lt.vu.entities.Course;
import lt.vu.entities.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import lt.vu.persistence.interfaces.ICoursesDAO;
@ApplicationScoped
@Alternative
public class CoursesAlter  extends CoursesDAO implements ICoursesDAO {
        @Inject
        EntityManager em;

        @Override
        public List<Course> loadAll() {
            System.out.println("ALTERNATIVE METHOD");

            return em
                    .createQuery("SELECT c FROM Course c WHERE SIZE(c.students) > 5", Course.class)
                    .getResultList();
        }
    }

