package lt.vu.persistence;

import lombok.var;
import lt.vu.entities.Faculty;
import lt.vu.entities.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StudentsDAO {
    @Inject
    private EntityManager em;
    public List<Student> loadAll() {
        return em.createNamedQuery("Student.findAll", Student.class).getResultList();
    }
    public List<Student> loadAvailable(Integer id){

     List<Student> a  = em.createNamedQuery("Students.In", Student.class).setParameter("id", id).getResultList();
     return em.createNamedQuery("Student.allAvailable", Student.class).setParameter("id", id).setParameter("names", a).getResultList();

    }
    public List<Faculty> faculties(Student student){
     return em.createNamedQuery("faculties", Faculty.class).setParameter("student", student).getResultList();

}
    public void persist(Student student){
        this.em.persist(student);
    }

    public Student findOne(Integer id){
        return em.find(Student.class, id);
    }
    @Transactional
    public Student update(Student student) {
        return em.merge(student);
    }


}
