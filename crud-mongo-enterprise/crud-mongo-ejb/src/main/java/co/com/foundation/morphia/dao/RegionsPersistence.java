package co.com.foundation.morphia.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.domain.RegionRequest;
import co.com.foundation.morphia.entities.Region;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.persistence.MongoConnection;

@Stateless(name = "RegionsPersistence")
public class RegionsPersistence implements Persistence<RegionRequest, Region> {

	private final Logger LOGGER = LogManager.getLogger(RegionsPersistence.class);

	@EJB
	private MongoConnection connection;

	@Override
	public void create(RegionRequest request) {
	}

	@Override
	public List<Region> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return connection.getDataStore().find(Region.class).asList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(RegionRequest request) {
	}

	@Override
	public void delete(RegionRequest request) {
	}

}
