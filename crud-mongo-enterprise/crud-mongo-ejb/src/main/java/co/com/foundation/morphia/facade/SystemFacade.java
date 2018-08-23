package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.EmployeeDAO;
import co.com.foundation.morphia.domain.Employee;
import co.com.foundation.morphia.entities.User;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.LoginRequest;

@Stateless
public class SystemFacade {

	@EJB
	private EmployeeDAO employeeDAO;

	private final Logger LOGGER = LogManager.getLogger(SystemFacade.class);

	public User login(final LoginRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- login method");
			return employeeDAO.login(request);
		} finally {
			LOGGER.info("end -- login method");
		}
	}

	public List<Employee> listEmployeesCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-employee-cmb method");
			return employeeDAO.listAllToCmb();
		} finally {
			LOGGER.info("end -- list-employee-cmb method");
		}
	}
}
