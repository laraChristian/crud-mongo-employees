package co.com.foundation.morphia.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.domain.Department;
import co.com.foundation.morphia.domain.Job;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.EntityNotFoundException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.facade.ModulesFacade;
import co.com.foundation.morphia.messages.DepartmentRequest;
import co.com.foundation.morphia.messages.DepartmentResponse;
import co.com.foundation.morphia.messages.JobRequest;
import co.com.foundation.morphia.messages.JobResponse;
import co.com.foundation.morphia.messages.JobResponse.JobResponseBuilder;
import co.com.foundation.morphia.messages.DepartmentResponse.DepartmentResponseBuilder;

@Stateless
@Path("/administrative-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministrativeApi {

	private final Logger LOGGER = LogManager.getLogger(AdministrativeApi.class);

	@EJB(beanName = "DepartmentFacade")
	private ModulesFacade<DepartmentRequest, Department> departmentFacade;

	@EJB(beanName = "JobFacade")
	private ModulesFacade<JobRequest, Job> jobFacade;

	@POST
	@Path("/create-department")
	public Response createDepartment(final DepartmentRequest request) {
		LOGGER.info("start -- create-department method");
		DepartmentResponseBuilder blResponse = DepartmentResponse.builder();
		try {
			if (request.isUpdate()) {
				departmentFacade.update(request);
				blResponse.message("The department was modified successfully");
			} else {
				departmentFacade.create(request);
				blResponse.message("The department was created successfully");
			}

			return Response.ok().entity(blResponse.success(true).build()).build();
		} catch (EntityNotFoundException | AvailabilityException | DuplicateNameException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- create-department method");
		}
	}

	@GET
	@Path("/list-departments")
	public Response listDepartments() {
		LOGGER.info("start -- list-departments method");
		DepartmentResponseBuilder blResponse = DepartmentResponse.builder();
		try {
			return Response.ok().entity(blResponse.success(true).departments(departmentFacade.listAll()).build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-departments method");
		}
	}

	@Path("/delete-department")
	@POST
	public Response deleteDepartment(final DepartmentRequest request) {
		LOGGER.info("start -- delete-department method for:{}", request.getDepartment().getId());
		DepartmentResponseBuilder blResponse = DepartmentResponse.builder();
		try {
			departmentFacade.delete(request);
			return Response.ok()
					.entity(blResponse.success(true).message("The department was deleted successfully").build())
					.build();
		} catch (AvailabilityException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete-department method for:{}", request.getDepartment().getId());
		}
	}

	@POST
	@Path("/create-job")
	public Response createJob(final JobRequest request) {
		LOGGER.info("start -- create-job method");
		JobResponseBuilder blResponse = JobResponse.builder();
		try {
			if (request.isUpdate()) {
				jobFacade.update(request);
				blResponse.message("The job was modified successfully");
			} else {
				jobFacade.create(request);
				blResponse.message("The job was created successfully");
			}
			return Response.ok().entity(blResponse.success(true).build()).build();
		} catch (DuplicateNameException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- create-job method");
		}
	}

	@GET
	@Path("/list-jobs")
	public Response listJobs() {
		LOGGER.info("start -- list-jobs method");
		JobResponseBuilder blResponse = JobResponse.builder();
		try {
			return Response.ok().entity(blResponse.success(true).jobs(jobFacade.listAll()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-jobs method");
		}
	}

	@Path("/delete-job")
	@POST
	public Response deleteJob(final JobRequest request) {
		LOGGER.info("start -- delete-job method for:{}", request.getJob().getId());
		DepartmentResponseBuilder blResponse = DepartmentResponse.builder();
		try {
			jobFacade.delete(request);
			return Response.ok().entity(blResponse.success(true).message("The job was deleted successfully").build())
					.build();
		} catch (AvailabilityException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(blResponse.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete-job method for:{}", request.getJob().getId());
		}
	}
}
