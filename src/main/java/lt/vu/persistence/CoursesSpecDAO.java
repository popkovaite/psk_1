package lt.vu.persistence;

import lt.vu.entities.Course;
import lt.vu.interceptors.LoggedInvocation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.persistence.EntityManager;

    @ApplicationScoped
    @LoggedInvocation
    @Specializes
    public class CoursesSpecDAO  extends CoursesDAO{
        @Inject
        private EntityManager em;
        @Override
        public void persist (Course course) {
            course.setName(course.getName()+" - special");
            System.out.println("specializes");
            this.em.persist(course);

        }
    }
