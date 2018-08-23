package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.Persistence;
import co.com.foundation.morphia.domain.Employee;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.EmployeeRequest;

@Stateless(name = "EmployeeFacade")
public class EmployeeFacade implements ModulesFacade<EmployeeRequest, Employee> {

	private final Logger LOGGER = LogManager.getLogger(EmployeeFacade.class);

	@EJB(beanName = "EmployeeDAO")
	private Persistence<EmployeeRequest, Employee> persistence;

	@Override
	public void create(EmployeeRequest request) throws PersistenceException {
		try {
			persistence.create(request);
		} finally {
			// TODO: handle finally clause
		}
	}

	@Override
	public List<Employee> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public void update(EmployeeRequest request) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(EmployeeRequest request) throws PersistenceException {
		// TODO Auto-generated method stub

	}

}
