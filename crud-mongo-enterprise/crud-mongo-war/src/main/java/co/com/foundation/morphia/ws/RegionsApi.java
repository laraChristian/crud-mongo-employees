package co.com.foundation.morphia.ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.domain.RegionRequest;
import co.com.foundation.morphia.domain.RegionResponse;
import co.com.foundation.morphia.entities.Region;
import co.com.foundation.morphia.facade.ModulesFacade;

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
			return Response.serverError()
					.entity(RegionResponse.builder().success(true).regions(facade.listAll()).build()).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}
}
