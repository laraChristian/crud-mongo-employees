package co.com.foundation.morphia.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.EmployeeDAO;
import co.com.foundation.morphia.domain.LoginRequest;
import co.com.foundation.morphia.entities.User;
import co.com.foundation.morphia.exceptions.PersistenceException;

@Stateless
public class SystemFacade {

	@EJB(beanName = "EmployeeDAO")
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
}
