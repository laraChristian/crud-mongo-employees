package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Location;
import co.com.foundation.morphia.entities.Department;
import co.com.foundation.morphia.entities.Locations;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.LocationRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.Validator;

@Stateless(name = "LocationDAO")
public class LocationDAO implements Persistence<LocationRequest, Location> {

	private final Logger LOGGER = LogManager.getLogger(LocationDAO.class);

	@EJB
	private MongoConnection connection;

	@Inject
	@Mappers(type = ComponentType.LOCATION)
	private Mapper<Location, Locations> mapper;

	@EJB(beanName = "CommonValidator")
	private Validator validator;

	@Override
	public void create(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			connection.getDataStore().save(mapper.marshall(request.getLocation()));
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Location> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return connection.getDataStore().find(Locations.class).asList().stream().map(mapper::unMarshall)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			connection.getDataStore().merge(mapper.marshall(request.getLocation()));
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(LocationRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method");
			Location location = request.getLocation();
			if (validator.isAssigned(new ObjectId(location.getId()), Locations.class, Department.class, "location")) {
				throw new AvailabilityException(
						"The location was found in multiple countries, this should be unassigned to can be delete");
			}
			connection.getDataStore().delete(mapper.marshall(location));
		} catch (AvailabilityException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
