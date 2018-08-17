package co.com.foundation.morphia.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.domain.Location;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.facade.ModulesFacade;
import co.com.foundation.morphia.messages.LocationRequest;
import co.com.foundation.morphia.messages.LocationResponse;
import co.com.foundation.morphia.messages.LocationResponse.LocationResponseBuilder;

@Stateless
@Path(value = "/locations-api")
@Produces(value = MediaType.APPLICATION_JSON)
public class LocationsApi {

	private final Logger LOGGER = LogManager.getLogger(LocationsApi.class);

	@EJB(beanName = "LocationsModule")
	private ModulesFacade<LocationRequest, Location> facade;

	@POST
	@Path("/create")
	public Response create(final LocationRequest request) {
		LOGGER.info("start -- create method");
		LocationResponseBuilder builder = LocationResponse.builder();
		try {
			if (request.isUpdate()) {
				facade.update(request);
				builder.message("The locations was edited successfully");
			} else {
				facade.create(request);
				builder.message("The locations was created successfully");
			}

			return Response.ok().entity(builder.success(true).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@GET
	@Path("/list")
	public Response listLocations() {
		LOGGER.info("start -- list method");
		LocationResponseBuilder builder = LocationResponse.builder();
		try {
			return Response.ok().entity(builder.success(true).locations(facade.listAll()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list method");
		}
	}

	@POST
	@Path("/delete")
	public Response delete(final LocationRequest request) {
		LOGGER.info("start -- delete method");
		LocationResponseBuilder builder = LocationResponse.builder();
		try {
			facade.delete(request);
			return Response.ok().entity(builder.success(true).message("The location was deleted successfully").build())
					.build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete method");
		}
	}
}
