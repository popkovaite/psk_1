package lt.vu.persistence.interfaces;

import lt.vu.entities.Course;

import javax.transaction.Transactional;
import java.util.List;

public interface ICoursesDAO {
     List<Course> loadAll();
     List<Course> loadAvailable(Integer id);
     void persist(Course course);
     Course findOne(Integer id);
     Course update(Course course);

}
