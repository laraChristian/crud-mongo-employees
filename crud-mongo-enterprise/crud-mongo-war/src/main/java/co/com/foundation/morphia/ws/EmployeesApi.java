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

import co.com.foundation.morphia.messages.EmployeeRequest;
import co.com.foundation.morphia.messages.EmployeeResponse;
import co.com.foundation.morphia.messages.EmployeeResponse.EmployeeResponseBuilder;
import co.com.foundation.morphia.messages.LoginRequest;
import co.com.foundation.morphia.messages.LoginResponse;
import co.com.foundation.morphia.messages.LoginResponse.LoginResponseBuilder;
import co.com.foundation.morphia.domain.Employee;
import co.com.foundation.morphia.exceptions.InvalidCredentialsException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.facade.ModulesFacade;
import co.com.foundation.morphia.facade.SystemFacade;

@Stateless
@Path(value = "/employees-api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class EmployeesApi {

	private final Logger LOGGER = LogManager.getLogger(EmployeesApi.class);

	@EJB
	private SystemFacade systemFacade;

	@EJB(beanName = "EmployeeFacade")
	private ModulesFacade<EmployeeRequest, Employee> facade;

	@POST
	@Path(value = "/login")
	public Response login(final LoginRequest request) {
		LOGGER.info("start -- login method");
		LoginResponseBuilder builder = LoginResponse.builder();
		try {
			return Response.ok().entity(builder.success(true).user(systemFacade.login(request)).build()).build();
		} catch (InvalidCredentialsException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(builder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- login method");
		}
	}

	@GET
	@Path("/list-employees")
	public Response listEmployees() {
		EmployeeResponseBuilder builder = EmployeeResponse.builder();
		try {
			LOGGER.info("start -- list-employees method");
			return Response.ok().entity(builder.success(true).employees(facade.listAll()).build()).build();
		} catch (Exception e) {
			return Response.ok().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-employees method");
		}
	}

	@GET
	@Path("/list-employees-cmb")
	public Response listToCmb() {
		EmployeeResponseBuilder builder = EmployeeResponse.builder();
		try {
			LOGGER.info("start -- list-employees method");
			return Response.ok().entity(builder.success(true).employees(systemFacade.listEmployeesCmb()).build())
					.build();
		} catch (Exception e) {
			return Response.ok().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list-employees method");
		}
	}

}
