package co.com.foundation.morphia.validators;

import javax.ejb.Local;

import org.bson.types.ObjectId;

import co.com.foundation.morphia.exceptions.PersistenceException;

@Local
public interface Validator {

	boolean nameAlReadyExist(final ObjectId id, final String name) throws PersistenceException;

	boolean isAssigned(final ObjectId id) throws PersistenceException;
}
