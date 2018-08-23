package co.com.foundation.morphia.validators.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.validators.Validator;

@Stateless(name = "CommonValidator")
public class CommonValidator implements Validator {

	private final Logger LOGGER = LogManager.getLogger(CommonValidator.class);

	@EJB
	private MongoConnection connection;

	/**
	 * @param id
	 *            send id to fin this name in other documents, else send null if
	 *            you want created a new document.
	 * @param name
	 *            name to look for in the collection
	 * @param className
	 *            is the class that represent the collection in MongoDB.
	 * @return boolean: if counter is bigger than 0 then true is returned else
	 *         false is returned
	 */
	@Override
	public <T> boolean nameAlReadyExist(ObjectId id, String column, String name, Class<T> className)
			throws PersistenceException {
		LOGGER.info("start -- name-already-exist method");
		Datastore datastore = connection.getDataStore();
		Query<T> nameAlreadyExist = datastore.createQuery(className).field(column).equal(name.trim());

		if (id != null) {
			nameAlreadyExist.and(nameAlreadyExist.criteria("_id").notEqual(id));
		}
		return datastore.getCount(nameAlreadyExist) > 0;
	}

	/**
	 * @param id
	 *            send id to fin this name in other documents, else send null if
	 *            you want created a new document.
	 * @param parentClass
	 *            is the class that represent the reference in the child
	 *            document.
	 * @param childClass
	 *            is the class child collection to look the reference
	 * @return boolean: if true then this id it was found inside of anyone child
	 *         reference, else false
	 */
	@Override
	public <P, C> boolean isAssigned(ObjectId id, Class<P> parentClass, Class<C> childClass, final String fieldFilter)
			throws PersistenceException {
		try {
			LOGGER.info("start -- is-assigned method for: {}", id);
			Datastore datastore = connection.getDataStore();
			P parentReference = datastore.find(parentClass).project("id", true).field("id").equal(id).get();
			Query<C> query = datastore.createQuery(childClass).filter(fieldFilter, parentReference);
			return datastore.getCount(query) > 0;
		} finally {
			LOGGER.info("start -- is-assigned method for: {}", id);
		}
	}

}
