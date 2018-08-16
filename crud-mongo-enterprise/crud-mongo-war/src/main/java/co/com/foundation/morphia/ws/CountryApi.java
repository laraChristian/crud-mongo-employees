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

import co.com.foundation.morphia.domain.Country;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.facade.ModulesFacade;
import co.com.foundation.morphia.messages.CountryRequest;
import co.com.foundation.morphia.messages.CountryResponse;
import co.com.foundation.morphia.messages.CountryResponse.CountryResponseBuilder;

@Stateless
@Path(value = "/countries-api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class CountryApi {

	private final Logger LOGGER = LogManager.getLogger(CountryApi.class);

	@EJB(beanName = "CountryFacade")
	private ModulesFacade<CountryRequest, Country> facade;

	@POST
	@Path(value = "/create-update-country")
	public Response createUpdateCountry(final CountryRequest request) {
		LOGGER.info("start -- create method");
		CountryResponseBuilder builder = CountryResponse.builder();
		try {
			if (request.isUpdate()) {
				facade.update(request);
				builder.message("The country was edited successfully");
			} else {
				facade.create(request);
				builder.message("The country was created successfully");
			}
			return Response.ok().entity(builder.success(true).build()).build();
		} catch (DuplicateNameException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(builder.message(e.getMessage()).success(false).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(builder.message(e.getMessage()).success(false).build()).build();
		} finally {
			LOGGER.info("end -- created method");
		}
	}

	@GET
	@Path(value = "/list-countries")
	public Response listCountries() {
		LOGGER.info("start -- list method");
		CountryResponseBuilder builder = CountryResponse.builder();
		try {
			return Response.ok().entity(builder.success(true).countries(facade.listAll()).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- list method");
		}
	}

	@POST
	@Path(value = "/delete-country")
	public Response deleteCountry(final CountryRequest request) {
		LOGGER.info("start -- delete method");
		CountryResponseBuilder builder = CountryResponse.builder();
		try {
			facade.delete(request);
			return Response.ok().entity(builder.success(true).message("The country was deleted successfully").build())
					.build();
		} catch (AvailabilityException e) {
			return Response.accepted().entity(builder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			return Response.serverError().entity(builder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete method");
		}
	}
}
