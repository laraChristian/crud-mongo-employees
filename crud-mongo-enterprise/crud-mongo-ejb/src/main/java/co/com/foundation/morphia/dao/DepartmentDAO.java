package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.commons.Utils;
import co.com.foundation.morphia.domain.Department;
import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.DepartmentRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.Validator;
import co.com.foundation.morphia.validators.annotation.Validators;
import co.com.foundation.morphia.validators.impl.AdministrativeValidator;

@Stateless(name = "DepartmentDAO")
public class DepartmentDAO implements Persistence<DepartmentRequest, Department> {

	private final Logger LOGGER = LogManager.getLogger(DepartmentDAO.class);

	@EJB
	private MongoConnection connection;

	@EJB(beanName = "CommonValidator")
	private Validator validator;

	@Inject
	@Validators(type = ComponentType.ADMNISTRATIVE)
	private AdministrativeValidator administrativeValidator;

	@Inject
	@Mappers(type = ComponentType.DEPARTMENT)
	private Mapper<co.com.foundation.morphia.domain.Department, co.com.foundation.morphia.entities.Department> mapper;

	@Override
	public void create(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");

			Department department = request.getDepartment();

			if (!Utils.isToReject.test(department.getManagerId())) {
				if (!administrativeValidator.managerIsAvailable(new ObjectId(department.getManagerId()),
						department.getId() != null ? new ObjectId(department.getId()) : null)) {
					throw new AvailabilityException("This manager already management other department");
				}
			}

			if (validator.nameAlReadyExist(department.getId() != null ? new ObjectId(department.getId()) : null,
					department.getName(), co.com.foundation.morphia.entities.Department.class)) {
				throw new DuplicateNameException("This name already assigned  to other department");
			}

			connection.getDataStore().save(mapper.marshall(department));

		} catch (AvailabilityException e) {
			throw e;
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Department> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list method");
			return connection.getDataStore().find(co.com.foundation.morphia.entities.Department.class).asList().stream()
					.map(mapper::unMarshall).collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list method");
		}
	}

	@Override
	public void update(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method");
			Department department = request.getDepartment();

			if (!Utils.isToReject.test(department.getManagerId())) {

				if (!administrativeValidator.isInToDepartment(new ObjectId(department.getManagerId()),
						new ObjectId(department.getId()))) {
					throw new EntityNotFoundException("This employee don't belong to department");
				}

				if (!administrativeValidator.managerIsAvailable(new ObjectId(department.getManagerId()),
						new ObjectId(department.getId()))) {
					throw new AvailabilityException("This manager already management other department");
				}
			}

			if (validator.nameAlReadyExist(new ObjectId(department.getId()), department.getName(),
					co.com.foundation.morphia.entities.Department.class)) {
				throw new DuplicateNameException("This name already assigned  to other department");
			}

			connection.getDataStore().save(mapper.marshall(department));
		} catch (EntityNotFoundException e) {
			throw e;
		} catch (AvailabilityException e) {
			throw e;
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method");
		}
	}

	@Override
	public void delete(DepartmentRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method");

			Department department = request.getDepartment();

			if (validator.isAssigned(new ObjectId(department.getId()),
					co.com.foundation.morphia.entities.Department.class, Employee.class, "department")) {
				throw new AvailabilityException(
						"This department was found in multiple employees, should be unassigned to proceed");
			}

			connection.getDataStore().delete(mapper.marshall(department));
		} catch (AvailabilityException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- delete method");
		}
	}

}
