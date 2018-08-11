package co.com.foundation.morphia.facade;

import java.util.List;

import co.com.foundation.morphia.exceptions.PersistenceException;

public interface ModulesFacade<I, O> {

	void create(final I request) throws PersistenceException;

	List<O> listAll() throws PersistenceException;

	void update(final I reuqest) throws PersistenceException;

	void delete(final I request) throws PersistenceException;
}
