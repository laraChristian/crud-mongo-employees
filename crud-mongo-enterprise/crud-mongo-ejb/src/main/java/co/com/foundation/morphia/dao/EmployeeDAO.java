package co.com.foundation.morphia.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.domain.EmployeeRequest;
import co.com.foundation.morphia.domain.LoginRequest;
import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.entities.User;
import co.com.foundation.morphia.exceptions.InvalidCredentialsException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.persistence.MongoConnection;

@Stateless(name = "EmployeeDAO")
@LocalBean
public class EmployeeDAO implements Persistence<EmployeeRequest, Employee> {

	@EJB
	private MongoConnection connection;

	@Override
	public void create(EmployeeRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Employee> listAll() throws PersistenceException {
		try {
			/*
			 * connection.getDataStore().find(Employee.class).project(
			 * "firstName", true).project("lastName", true) .project("email",
			 * true).project("phoneNumber", true).project("hireDate", true)
			 * .project("identification", true).project("manager",
			 * true).project("job", true).proje;
			 */
			return null;
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(EmployeeRequest request) {
		// TODO Auto-generated method stub

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
