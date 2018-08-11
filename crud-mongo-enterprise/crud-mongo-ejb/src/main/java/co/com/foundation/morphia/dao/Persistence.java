package co.com.foundation.morphia.dao;

import java.util.List;

import co.com.foundation.morphia.exceptions.PersistenceException;

public interface Persistence<I, O> {

	void create(final I request);

	List<O> listAll() throws PersistenceException;

	void update(final I request);

	void delete(final I request);

}
