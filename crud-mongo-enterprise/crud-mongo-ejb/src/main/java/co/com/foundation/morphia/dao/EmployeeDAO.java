package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.commons.Utils;
import co.com.foundation.morphia.domain.Employee;
import co.com.foundation.morphia.entities.User;
import co.com.foundation.morphia.exceptions.InvalidCredentialsException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.EmployeeRequest;
import co.com.foundation.morphia.messages.LoginRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;

@Stateless(name = "EmployeeDAO")
@LocalBean
public class EmployeeDAO implements Persistence<EmployeeRequest, Employee> {

	private final Logger LOGGER = LogManager.getLogger(EmployeeDAO.class);

	@EJB
	private MongoConnection connection;

	@Inject
	@Mappers(type = ComponentType.EMPLOYEE)
	private Mapper<Employee, co.com.foundation.morphia.entities.Employee> mapper;

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
			return connection.getDataStore().find(co.com.foundation.morphia.entities.Employee.class).asList().stream()
					.map(mapper::unMarshall).collect(Collectors.toList());
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

			Query<co.com.foundation.morphia.entities.Employee> query = connection.getDataStore()
					.createQuery(co.com.foundation.morphia.entities.Employee.class);
			query.project("firstName", true).project("email", true)
					.filter("email.email", request.getUser().getUserName())
					.and(query.criteria("email.password").equal(request.getUser().getPassword()));

			co.com.foundation.morphia.entities.Employee employee = query.get();

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

	public List<Employee> listAllToCmb() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return connection.getDataStore().find(co.com.foundation.morphia.entities.Employee.class)
					.project("firstName", true).project("lastName", true).project("job", true).asList().parallelStream()
					.filter((entity) -> {
						return entity.getJob().getJobTittle().equals(Utils.JOBS.PRESIDENT.toString())
								|| entity.getJob().getJobTittle().contains(Utils.JOBS.MANAGER.toString());
					}).sequential().map(mapper::unMarshall).collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}
}
