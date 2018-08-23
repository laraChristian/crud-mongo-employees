package co.com.foundation.morphia.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.validators.Validator;
import co.com.foundation.morphia.commons.Utils;
import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.entities.Job;
import co.com.foundation.morphia.exceptions.AvailabilityException;
import co.com.foundation.morphia.exceptions.DuplicateNameException;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.mapper.Mapper;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.messages.JobRequest;
import co.com.foundation.morphia.persistence.MongoConnection;
import co.com.foundation.morphia.types.ComponentType;

@Stateless(name = "JobDAO")
public class JobDAO implements Persistence<JobRequest, co.com.foundation.morphia.domain.Job> {

	private final Logger LOGGER = LogManager.getLogger(JobDAO.class);

	@EJB
	private MongoConnection connection;

	@EJB(beanName = "CommonValidator")
	private Validator validator;

	@Inject
	@Mappers(type = ComponentType.JOB)
	private Mapper<co.com.foundation.morphia.domain.Job, Job> mapper;

	@Override
	public void create(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			co.com.foundation.morphia.domain.Job job = request.getJob();

			if (validator.nameAlReadyExist(null, Utils.Columns.JOBTITTLE.getId(), job.getJobTittle(), Job.class)) {
				throw new DuplicateNameException("This name already assigned to other job");
			}

			connection.getDataStore().save(mapper.marshall(job));
		} catch (DuplicateNameException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<co.com.foundation.morphia.domain.Job> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return connection.getDataStore().find(Job.class).asList().stream().map(mapper::unMarshall)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method for:{}", request.getJob().getId());
			co.com.foundation.morphia.domain.Job job = request.getJob();

			if (validator.nameAlReadyExist(new ObjectId(job.getId()), Utils.Columns.JOBTITTLE.getId(),
					job.getJobTittle(), Job.class)) {
				throw new DuplicateNameException("This name already assigned to other job");
			}

			connection.getDataStore().merge(mapper.marshall(job));
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("end -- update method for:{}", request.getJob().getId());
		}
	}

	@Override
	public void delete(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method for:{}", request.getJob().getId());
			co.com.foundation.morphia.domain.Job job = request.getJob();

			if (validator.isAssigned(new ObjectId(job.getId()), Job.class, Employee.class, Utils.Columns.JOB.getId())) {
				throw new AvailabilityException(
						"This job was found in multiple employees, should be unassigned to proceed");
			}

			connection.getDataStore().delete(mapper.marshall(job));
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			LOGGER.info("start -- delete method for:{}", request.getJob().getId());
		}
	}

}
