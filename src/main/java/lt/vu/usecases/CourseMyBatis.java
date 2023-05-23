package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.CourseMapper;
import lt.vu.mybatis.model.Course;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class CourseMyBatis {
    @Inject
    private CourseMapper courseMapper;

    @Getter
    private List<Course> allCourse;

    @Getter @Setter
    private Course courseToCreate = new Course();

    @PostConstruct
    public void init() {
        this.loadAllTeams();
    }

    private void loadAllTeams() {
        this.allCourse = courseMapper.selectAll();
    }

    @Transactional
    public String createCourse() {
        courseMapper.insert(courseToCreate);
        return "/myBatis/course?faces-redirect=true";
    }
}
