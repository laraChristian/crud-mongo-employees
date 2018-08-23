package co.com.foundation.morphia.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Job;
import co.com.foundation.morphia.domain.Job.JobBuilder;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.types.ComponentType;

@ApplicationScoped
@Mappers(type = ComponentType.JOB)
public class JobMapper implements Mapper<Job, co.com.foundation.morphia.entities.Job> {

	private final Logger LOGGER = LogManager.getLogger(JobMapper.class);

	@Override
	public co.com.foundation.morphia.entities.Job marshall(Job input) {
		try {
			LOGGER.info("start -- marshall method");
			co.com.foundation.morphia.entities.Job.JobBuilder builder = co.com.foundation.morphia.entities.Job
					.builder();
			return builder.id(input.getId() != null ? new ObjectId(input.getId()) : null)
					.jobTittle(input.getJobTittle().trim()).maxSalary(input.getMaxSalary()).minSalary(input.getMinSalary())
					.build();
		} finally {
			LOGGER.info("end -- marshall method");
		}
	}

	@Override
	public Job unMarshall(co.com.foundation.morphia.entities.Job input) {
		try {
			LOGGER.info("start -- un-marshall method");
			JobBuilder builder = Job.builder();
			return builder.id(input.getId().toHexString()).jobTittle(input.getJobTittle())
					.maxSalary(input.getMaxSalary()).minSalary(input.getMinSalary()).build();
		} finally {
			LOGGER.info("end -- un-marshall method");
		}
	}

}
