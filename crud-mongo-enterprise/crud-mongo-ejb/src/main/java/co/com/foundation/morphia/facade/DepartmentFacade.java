package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.Persistence;
import co.com.foundation.morphia.domain.Department;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.DepartmentRequest;

@Stateless(name = "DepartmentFacade")
public class DepartmentFacade implements ModulesFacade<DepartmentRequest, Department> {

	private final Logger LOGGER = LogManager.getLogger(DepartmentFacade.class);

	@EJB(beanName = "DepartmentDAO")
	private Persistence<DepartmentRequest, Department> persistence;

	@Override
	public void create(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			persistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Department> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- list method");
		}
	}

	@Override
	public void update(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			persistence.update(request);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method");
			persistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
