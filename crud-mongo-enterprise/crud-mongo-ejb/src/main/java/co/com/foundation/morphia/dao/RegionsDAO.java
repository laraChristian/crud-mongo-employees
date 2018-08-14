package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.entities.Region;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.RegionRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.Validator;
import co.com.foundation.morphia.validators.annotation.Validators;

@Stateless(name = "RegionsDAO")
public class RegionsDAO implements Persistence<RegionRequest, co.com.foundation.morphia.domain.Region> {

	private final Logger LOGGER = LogManager.getLogger(RegionsDAO.class);

	@EJB
	private MongoConnection connection;

	@Inject
	@Validators(type = ComponentType.REGION)
	private Validator validator;

	@Inject
	@Mappers(type = ComponentType.REGION)
	private Mapper<co.com.foundation.morphia.domain.Region, co.com.foundation.morphia.entities.Region> mapper;

	@Override
	public void create(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- created method");
			co.com.foundation.morphia.domain.Region region = request.getRegion();

			if (validator.nameAlReadyExist(region.getId() != null ? new ObjectId(region.getId()) : null,
					region.getName())) {
				throw new DuplicateNameException("This name already assigned to another region");
			}

			connection.getDataStore().save(mapper.map(region));
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- created method");
		}
	}

	@Override
	public List<co.com.foundation.morphia.domain.Region> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return connection.getDataStore().find(Region.class).asList().stream().map((entity) -> {
				return new co.com.foundation.morphia.domain.Region(entity.getId().toHexString(), entity.getName());
			}).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method for:{}", request.getRegion().getId());
			co.com.foundation.morphia.domain.Region region = request.getRegion();
			if (validator.nameAlReadyExist(new ObjectId(region.getId()), region.getName())) {
				throw new DuplicateNameException("This name already assigned to another region");
			}
			connection.getDataStore().merge(mapper.map(region));
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method for:{}", request.getRegion().getId());
		}
	}

	@Override
	public void delete(RegionRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method for:{}", request.getRegion().getId());
			co.com.foundation.morphia.domain.Region region = request.getRegion();
			if (validator.isAssigned(new ObjectId(region.getId()))) {
				throw new AvailabilityException(
						"the Americas region was found in multiple countries, this should be unassigned to can be delete");
			}
			connection.getDataStore().delete(mapper.map(region));
		} catch (AvailabilityException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method for:{}", request.getRegion().getId());
		}
	}

}
