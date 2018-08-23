package co.com.foundation.morphia.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.morphia.dao.Persistence;
import co.com.foundation.morphia.domain.Job;
import co.com.foundation.morphia.exceptions.PersistenceException;
import co.com.foundation.morphia.messages.JobRequest;

@Stateless(name = "JobFacade")
public class JobFacade implements ModulesFacade<JobRequest, Job> {

	private final Logger LOGGER = LogManager.getLogger(JobFacade.class);

	@EJB(beanName = "JobDAO")
	private Persistence<JobRequest, co.com.foundation.morphia.domain.Job> persistence;

	@Override
	public void create(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- create method");
			persistence.create(request);
		} finally {
			LOGGER.info("end -- create method");
		}
	}

	@Override
	public List<Job> listAll() throws PersistenceException {
		try {
			LOGGER.info("start -- list-all method");
			return persistence.listAll();
		} finally {
			LOGGER.info("end -- list-all method");
		}
	}

	@Override
	public void update(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- update method for:{}", request.getJob().getId());
			persistence.update(request);
		} finally {
			LOGGER.info("end -- update method for:{}", request.getJob().getId());
		}
	}

	@Override
	public void delete(JobRequest request) throws PersistenceException {
		try {
			LOGGER.info("start -- delete method for:{}", request.getJob().getId());
			persistence.delete(request);
		} finally {
			LOGGER.info("end -- delete method for:{}", request.getJob().getId());
		}
	}

}
