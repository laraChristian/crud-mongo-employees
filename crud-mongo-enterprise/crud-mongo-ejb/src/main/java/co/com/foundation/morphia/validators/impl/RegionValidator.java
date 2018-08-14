package co.com.foundation.morphia.validators.impl;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.entities.Country;
import co.com.foundation.morphia.entities.Region;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.Validator;
import co.com.foundation.morphia.validators.annotation.Validators;

@ApplicationScoped
@Validators(type = ComponentType.REGION)
public class RegionValidator implements Validator {

	private final Logger LOGGER = LogManager.getLogger(RegionValidator.class);

	@EJB
	private MongoConnection connection;

	@Override
	public boolean nameAlReadyExist(ObjectId id, String name) throws PersistenceException {
		try {
			LOGGER.info("start -- name-already-exist method");
			Datastore datastore = connection.getDataStore();
			Query<Region> nameAlreadyExist = datastore.createQuery(Region.class).field("name").equal(name.trim());

			if (id != null) {
				nameAlreadyExist.and(nameAlreadyExist.criteria("_id").notEqual(id));
			}

			return datastore.getCount(nameAlreadyExist) > 0;
		} finally {
			LOGGER.info("end -- name-already-exist method");
		}
	}

	@Override
	public boolean isAssigned(ObjectId id) throws PersistenceException {
		try {
			LOGGER.info("start -- is-assigned method for: {}", id);
			Datastore datastore = connection.getDataStore();
			Region region = datastore.find(Region.class).project("id", true).field("id").equal(id).get();
			Query<Country> query = datastore.createQuery(Country.class).filter("region", region);
			return datastore.getCount(query) > 0;
		} finally {
			LOGGER.info("end -- is-assigned method for: {}", id);
		}
	}

}
