package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.commons.Utils;
import co.com.foundation.morphia.domain.Employee;
import co.com.foundation.morphia.entities.Department;
import co.com.foundation.morphia.entities.User;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.InvalidCredentialsException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.EmployeeRequest;
import co.com.foundation.morphia.messages.LoginRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.Validator;
import co.com.foundation.morphia.validators.annotation.Validators;
import co.com.foundation.morphia.validators.impl.AdministrativeValidator;

@Stateless(name = "EmployeeDAO")
@LocalBean
public class EmployeeDAO implements Persistence<EmployeeRequest, Employee> {

	private final Logger LOGGER = LogManager.getLogger(EmployeeDAO.class);

	@EJB
	private MongoConnection connection;

	@EJB(beanName = "CommonValidator")
	private Validator commonValidator;

	@Inject
	@Validators(type = ComponentType.ADMNISTRATIVE)
	private AdministrativeValidator administrativeValidator;

	@Inject
	@Mappers(type = ComponentType.EMPLOYEE)
	private Mapper<Employee, co.com.foundation.morphia.entities.Employee> mapper;

	@Override
	public void create(EmployeeRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");

			if (commonValidator.nameAlReadyExist(null, Utils.Columns.EMAIL.getId() + "." + "email",
					request.getEmployee().getEmail(), co.com.foundation.morphia.entities.Employee.class)) {
				throw new DuplicateNameException("This email already exist");
			}

			connection.getDataStore().save(mapper.marshall(request.getEmployee()));
		} catch (AvailabilityException | DuplicateNameException e) {
			throw e;
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
			return connection.getDataStore().find(co.com.foundation.morphia.entities.Employee.class)
					.project("firstName", true).project("lastName", true).project("email", true)
					.project("phoneNumber", true).project("hireDate", true).project("identification", true)
					.project("manager", true).project("job", true).project("department", true).asList().stream()
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
			Employee employee = request.getEmployee();
			Datastore datastore = connection.getDataStore();

			ObjectId id = new ObjectId(request.getEmployee().getId());

			if (administrativeValidator.isManager(Department.class, id)) {
				Department oldDepartment = datastore.find(co.com.foundation.morphia.entities.Employee.class)
						.project("department", true).filter("id", id).get().getDepartment();

				if (!employee.getDepartmentId().equals(oldDepartment.getId().toHexString())) {
					throw new AvailabilityException(
							"This employee is manager to other department, should be unassigned to proceed");
				}
			}

			if (commonValidator.nameAlReadyExist(new ObjectId(employee.getId()),
					Utils.Columns.EMAIL.getId() + "." + "email", request.getEmployee().getEmail(),
					co.com.foundation.morphia.entities.Employee.class)) {
				throw new DuplicateNameException("This email already exist");
			}

			datastore.merge(mapper.marshall(employee));
		} catch (AvailabilityException | DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(EmployeeRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method for:{}", request.getEmployee().getId());
			if (administrativeValidator.isManager(Department.class, new ObjectId(request.getEmployee().getId()))
					|| administrativeValidator.isManager(co.com.foundation.morphia.entities.Employee.class,
							new ObjectId(request.getEmployee().getId()))) {
				throw new AvailabilityException("This employee is manager, should be unassigned to proced");
			}

			connection.getDataStore().delete(mapper.marshall(request.getEmployee()));
		} catch (AvailabilityException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method for:{}", request.getEmployee().getId());
		}
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
								|| entity.getJob().getJobTittle().equals(Utils.JOBS.VICEPRESIDENT.toString())
								|| entity.getJob().getJobTittle().contains(Utils.JOBS.MANAGER.toString());
					}).sequential().map(mapper::unMarshall).collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}
}
