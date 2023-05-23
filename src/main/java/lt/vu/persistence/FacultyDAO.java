package lt.vu.persistence;

import lt.vu.entities.Faculty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class FacultyDAO {

    @Inject
    private EntityManager em;

    public List<Faculty> loadAll() {
        return em.createNamedQuery("Faculty.findAll", Faculty.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Faculty faculty){
        this.em.persist(faculty);
    }

    public Faculty findOne(Integer id) {
        return em.find(Faculty.class, id);
    }
}
