package lt.vu.rest;

import lombok.*;
import lt.vu.entities.Course;
import lt.vu.persistence.CoursesDAO;
import lt.vu.rest.contracts.CourseDto;
import lt.vu.rest.mapper.CourseService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/courses")
public class CoursesController {

    @Inject
    @Setter @Getter
    private CoursesDAO coursesDAO;

    @Inject
    private CourseService courseService;

    @Path("/get{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Course course = coursesDAO.findOne(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CourseDto courseDto = courseService.getCourseWithStudents(course);
        System.out.println(course.getStudents());

        return Response.ok(courseDto).build();
    }
    @Path("/post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setId(courseDto.getId());
        course.setCreditNr(courseDto.getCreditNr());
        coursesDAO.persist(course);
        return Response.ok(Response.Status.OK).build();
    }

    @Path("/put{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer courseId,
            CourseDto courseDto) {
        try {
            Course existingCourse = coursesDAO.findOne(courseId);
            if (existingCourse == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingCourse.setName(courseDto.getName());
            existingCourse.setCreditNr(courseDto.getCreditNr());
            coursesDAO.update(existingCourse);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
