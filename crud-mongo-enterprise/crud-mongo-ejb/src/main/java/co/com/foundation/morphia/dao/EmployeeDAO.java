package co.com.foundation.morphia.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.entities.User;
import co.com.foundation.morphia.exceptions.InvalidCredentialsException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.EmployeeRequest;
import co.com.foundation.morphia.messages.LoginRequest;
import co.com.foundation.morphia.persistence.MongoConnection;

@Stateless
@LocalBean
public class EmployeeDAO implements Persistence<EmployeeRequest, Employee> {

	private final Logger LOGGER = LogManager.getLogger(EmployeeDAO.class);

	@EJB
	private MongoConnection connection;

	@Override
	public void create(EmployeeRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Employee> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return null;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(EmployeeRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(EmployeeRequest request) {
		// TODO Auto-generated method stub

	}

	public User login(final LoginRequest request) throws PersistenceException {
		try {

			Query<Employee> query = connection.getDataStore().createQuery(Employee.class);
			query.project("firstName", true).project("email", true)
					.filter("email.email", request.getUser().getUserName())
					.and(query.criteria("email.password").equal(request.getUser().getPassword()));

			Employee employee = query.get();

			if (employee == null) {
				throw new InvalidCredentialsException("Invalid credentials");
			}

			request.getUser().setId(employee.getId());
			return request.getUser();

		} catch (InvalidCredentialsException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		}
	}
}
