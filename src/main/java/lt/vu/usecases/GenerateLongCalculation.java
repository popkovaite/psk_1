package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Course;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.CoursesDAO;
import lt.vu.services.LongCalculationComponent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GenerateLongCalculation implements Serializable {
    @Inject
    LongCalculationComponent studentCount;
    @Inject
    private CoursesDAO courseDAO;


    @Getter @Setter
    private Course course;

    private CompletableFuture<Integer> studentNumberTask = null;
    @Setter @Getter
    private Integer studentNumber;
    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer courseId = Integer.parseInt(requestParameters.get("courseId"));
        this.course = courseDAO.findOne(courseId);
    }
    @LoggedInvocation
    public String calculateNumber() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        studentNumberTask =  CompletableFuture.supplyAsync(() -> studentCount.StudentCount(course.getStudentNumber()));
        studentNumberTask.thenAccept(result -> {
            studentNumber = result;
        });

        return  "courseDetails?faces-redirect=true&amp;courseId=" + requestParameters.get("courseId");

    }

    public boolean isCalculationRunning() {
        return studentNumberTask != null && !studentNumberTask.isDone();
    }

    public String getNumberCalculationStatus() throws ExecutionException, InterruptedException {
        if (studentNumberTask == null) {
            return null;
        } else if (isCalculationRunning()) {
            return "student calculation in progress";
        }
        return "Number of Student in the course: " + studentNumber;
    }
}
