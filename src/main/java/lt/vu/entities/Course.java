package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Course.findAll", query = "select a from Course as a"),
        @NamedQuery(name = "Course.findAvailabe", query = "select distinct c from Course c  left join fetch c.students stud where stud.id <> :id and c not in :names"),
        @NamedQuery(name = "Course.In", query = "select c from Course c left join c.students stud  where stud.id = :id")
})
@Table(name = "COURSE")
@Getter @Setter
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    @Column(name = "NAME")
    private String name;

    @Column(name = "CREDIT_NUMBER")
    private Integer creditNr;

    @ManyToOne
    @JoinColumn(name="FACULTY_ID")
    private Faculty faculty;


    @ManyToMany(mappedBy="courses")
    private List<Student> students = new ArrayList<>();

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public Course() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Integer getStudentNumber(){
        Integer number = 0;

        for(Student student : this.getStudents())
        {
            number ++;
        }

        return number;
    }
}
