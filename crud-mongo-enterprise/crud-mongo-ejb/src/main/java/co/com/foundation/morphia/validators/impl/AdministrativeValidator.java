package co.com.foundation.morphia.validators.impl;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import co.com.foundation.morphia.entities.Department;
import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.validators.annotation.Validators;

@ApplicationScoped
@Validators(type = ComponentType.ADMNISTRATIVE)
public class AdministrativeValidator {

	private final Logger LOGGER = LogManager.getLogger(AdministrativeValidator.class);

	@EJB
	private MongoConnection connection;

	public boolean managerIsAvailable(final ObjectId managerId, final ObjectId departmentId)
			throws PersistenceException {
		try {
			LOGGER.info("start -- manager-is-available method");
			Query<Department> query = connection.getDataStore().createQuery(Department.class);

			if (departmentId != null) {
				query.field("id").notEqual(departmentId);
			}

			query.field("manager").equal(Employee.builder().id((managerId)).build());
			return connection.getDataStore().getCount(query) == 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- manager-is-available method");
		}
	}

	public boolean isInToDepartment(final ObjectId employeeId, final ObjectId departmentId)
			throws PersistenceException {
		try {
			LOGGER.info("start -- is-in-to-department method");
			Query<Employee> query = connection.getDataStore().createQuery(Employee.class);
			query.field("id").equal(employeeId)
					.and(query.criteria("department").equal(Department.builder().id(departmentId).build()));
			return connection.getDataStore().getCount(query) > 0;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- is-in-to-department method");
		}

	}

}
