package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.Serializable;
import java.util.Objects;

    @Entity
    @NamedQueries({
            @NamedQuery(name = "Student.findAll", query = "select a from Student as a"),
            @NamedQuery(name = "Student.allAvailable", query = "select distinct stud from Student stud left join  stud.courses c  where c.id <> :id and stud not in :names"),
            @NamedQuery(name = "Students.In", query = "select stud from Student stud left join  stud.courses c  where c.id = :id"),
            @NamedQuery(name = "faculties", query = "select distinct fac from Faculty fac left join fac.courses c  where  c.students = :student")
   })
    @Table(name = "Student")
    @Getter@Setter
    public class Student implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Size(max = 50)
        @Column(name = "NAME")
        private String name;

        @Column(name = "NUMBER")
        private String studentNr;

        @ManyToMany
        @JoinTable(name="STUDENT_COURSE")
        private List<Course> courses = new ArrayList<>();

        @Version
        @Column(name = "OPT_LOCK_VERSION")
        private Integer version;

        public Student() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            lt.vu.entities.Student student = (lt.vu.entities.Student) o;
            return Objects.equals(id, student.id) &&
                    Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
        public HashSet<String> getFaculties(){
            HashSet<String> facultyList = new HashSet<>();
            for(Course course : this.getCourses())
            {
                facultyList.add(course.getFaculty().getName());
            }
            return facultyList;
        }

    }


