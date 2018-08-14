package co.com.foundation.morphia.ws;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.messages.LoginRequest;
import co.com.foundation.morphia.messages.LoginResponse;
import co.com.foundation.morphia.messages.LoginResponse.LoginResponseBuilder;
import co.com.foundation.morphia.exceptions.InvalidCredentialsException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.facade.SystemFacade;

@Path(value = "/employees-api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class EmployeesApi {

	private final Logger LOGGER = LogManager.getLogger(EmployeesApi.class);

	@EJB
	private SystemFacade systemFacade;

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
}
