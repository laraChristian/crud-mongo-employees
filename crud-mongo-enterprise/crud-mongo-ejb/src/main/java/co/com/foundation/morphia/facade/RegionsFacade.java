package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.Persistence;
import co.com.foundation.morphia.domain.RegionRequest;
import co.com.foundation.morphia.entities.Region;
import co.com.foundation.morphia.exceptions.PersistenceException;

@Stateless(name = "RegionsFacade")
public class RegionsFacade implements ModulesFacade<RegionRequest, Region> {

	private final Logger LOGGER = LogManager.getLogger(RegionsFacade.class);

	@EJB(beanName = "RegionsPersistence")
	private Persistence<RegionRequest, Region> persistence;

	@Override
	public void create(RegionRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Region> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(RegionRequest reuqest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(RegionRequest request) {
		// TODO Auto-generated method stub

	}

}
