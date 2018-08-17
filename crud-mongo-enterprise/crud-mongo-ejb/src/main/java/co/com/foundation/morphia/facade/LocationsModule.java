package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.Persistence;
import co.com.foundation.morphia.domain.Location;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.LocationRequest;

@Stateless(name = "LocationsModule")
public class LocationsModule implements ModulesFacade<LocationRequest, Location> {

	private final Logger LOGGER = LogManager.getLogger(LocationsModule.class);

	@EJB(beanName = "LocationDAO")
	private Persistence<LocationRequest, Location> persistence;

	@Override
	public void create(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			persistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Location> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			persistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method");
			persistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
