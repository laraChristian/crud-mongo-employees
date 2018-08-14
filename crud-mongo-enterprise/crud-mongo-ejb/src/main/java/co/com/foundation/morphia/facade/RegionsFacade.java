package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.Persistence;
import co.com.foundation.morphia.domain.Region;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.RegionRequest;

@Stateless(name = "RegionsFacade")
public class RegionsFacade implements ModulesFacade<RegionRequest, Region> {

	private final Logger LOGGER = LogManager.getLogger(RegionsFacade.class);

	@EJB(beanName = "RegionsDAO")
	private Persistence<RegionRequest, Region> persistence;

	@Override
	public void create(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			persistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
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
	public void update(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			persistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method for:{}", request.getRegion().getId());
			persistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method for:{}", request.getRegion().getId());
		}
	}

}
