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

import co.com.foundation.morphia.messages.RegionResponse.RegionResponseBuilder;
import co.com.foundation.morphia.domain.Region;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.facade.ModulesFacade;
import co.com.foundation.morphia.messages.RegionRequest;
import co.com.foundation.morphia.messages.RegionResponse;

@Stateless
@Path(value = "/regions-api")
@Produces(value = MediaType.APPLICATION_JSON)
public class RegionsApi {

	private final Logger LOGGER = LogManager.getLogger(RegionsApi.class);

	@EJB(beanName = "RegionsFacade")
	ModulesFacade<RegionRequest, Region> facade;

	@GET
	@Path(value = "/list-regions")
	public Response listAllRegions() {
		try {
			LOGGER.info("start -- list-all method");
			return Response.ok().entity(RegionResponse.builder().success(true).regions(facade.listAll()).build())
					.build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@POST
	@Path(value = "/create-update-region")
	public Response createUpdateRegion(final RegionRequest request) {
		LOGGER.info("start -- create-update-region method");
		RegionResponseBuilder rpBuilder = RegionResponse.builder();
		try {
			if (request.isUpdate()) {
				facade.update(request);
				rpBuilder.message("The region " + request.getRegion().getName() + " was edited successfully");
			} else {
				facade.create(request);
				rpBuilder.message("The region " + request.getRegion().getName() + " was created successfully");
			}
			return Response.ok().entity(rpBuilder.success(true).build()).build();
		} catch (DuplicateNameException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(rpBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(rpBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- create-update-region method");
		}
	}

	@POST
	@Path(value = "/delete-region")
	public Response deleteRegion(final RegionRequest request) {
		LOGGER.info("start -- delete-region method for:{}", request.getRegion().getId());
		RegionResponseBuilder rpBuilder = RegionResponse.builder();
		try {
			facade.delete(request);
			return Response.ok().entity(rpBuilder.success(true).message("The region was deleted successfully").build())
					.build();
		} catch (AvailabilityException e) {
			LOGGER.error(e.getMessage());
			return Response.accepted().entity(rpBuilder.success(false).message(e.getMessage()).build()).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(rpBuilder.success(false).message(e.getMessage()).build()).build();
		} finally {
			LOGGER.info("end -- delete-region method for:{}", request.getRegion().getId());
		}
	}
}
