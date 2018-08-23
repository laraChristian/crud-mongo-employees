package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Country;
import co.com.foundation.morphia.entities.Locations;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.CountryRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.Validator;

@Stateless(name = "CountryDAO")
public class CountryDAO implements Persistence<CountryRequest, co.com.foundation.morphia.domain.Country> {

	private final Logger LOGGER = LogManager.getLogger(CountryDAO.class);

	@EJB
	private MongoConnection connection;

	@EJB(beanName = "CommonValidator")
	private Validator validator;

	@Inject
	@Mappers(type = ComponentType.COUNTRY)
	private Mapper<co.com.foundation.morphia.domain.Country, co.com.foundation.morphia.entities.Country> mapper;

	@Override
	public void create(CountryRequest request) throws PersistenceException {
		LOGGER.info("start -- create method");
		try {
			Country country = request.getCountry();
			if (validator.nameAlReadyExist(country.getId() != null ? new ObjectId(country.getId()) : null,
					country.getName(), co.com.foundation.morphia.entities.Country.class)) {
				throw new DuplicateNameException("This name already exist");
			}

			connection.getDataStore().save(mapper.marshall(country));
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Country> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list method");
			return connection.getDataStore().find(co.com.foundation.morphia.entities.Country.class).asList().stream()
					.map((entity) -> {
						return new Country(entity.getId().toHexString(), entity.getCountryName(),
								entity.getRegion().getId().toHexString(), entity.getRegion().getName());
					}).collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list method");
		}
	}

	@Override
	public void update(CountryRequest request) throws PersistenceException {
		LOGGER.info("start -- update method for:{}", request.getCountry().getId());
		try {
			Country country = request.getCountry();
			if (validator.nameAlReadyExist(new ObjectId(country.getId()), country.getName(),
					co.com.foundation.morphia.entities.Country.class)) {
				throw new DuplicateNameException("This name already assigned to other country");
			}

			connection.getDataStore().save(mapper.marshall(country));
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method for:{}", request.getCountry().getId());
		}
	}

	@Override
	public void delete(CountryRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method for: {}", request.getCountry().getId());
			Country country = request.getCountry();

			if (validator.isAssigned(new ObjectId(country.getId()), co.com.foundation.morphia.entities.Country.class,
					Locations.class, "country")) {
				throw new AvailabilityException(
						"The country was found in multiple locations, this should be unassigned to can be delete ");
			}

			connection.getDataStore().delete(mapper.marshall(country));
		} catch (AvailabilityException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method for: {}", request.getCountry().getId());
		}
	}

}