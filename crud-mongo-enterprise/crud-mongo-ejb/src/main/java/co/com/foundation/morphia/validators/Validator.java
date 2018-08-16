package co.com.foundation.morphia.validators;

import javax.ejb.Local;

import org.bson.types.ObjectId;

import co.com.foundation.morphia.exceptions.PersistenceException;

@Local
public interface Validator {

	<T> boolean nameAlReadyExist(final ObjectId id, final String name, Class<T> className) throws PersistenceException;

	<P, C> boolean isAssigned(final ObjectId id, final Class<P> parentClass, final Class<C> childClass, final String fieldFilter)
			throws PersistenceException;
}
