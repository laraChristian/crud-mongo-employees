package co.com.foundation.morphia.dao;

import java.util.List;

import javax.ejb.Local;

import co.com.foundation.morphia.exceptions.PersistenceException;

@Local
public interface Persistence<I, O> {

	void create(final I request) throws PersistenceException;

	List<O> listAll() throws PersistenceException;

	void update(final I request) throws PersistenceException;

	void delete(final I request) throws PersistenceException;

}
