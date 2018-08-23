package co.com.foundation.morphia.mapper;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Employee;
import co.com.foundation.morphia.entities.Department;
import co.com.foundation.morphia.entities.Email;
import co.com.foundation.morphia.entities.Employee.EmployeeBuilder;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.types.ComponentType;
import co.com.foundation.morphia.entities.Job;

@ApplicationScoped
@Mappers(type = ComponentType.EMPLOYEE)
public class EmployeeMapper implements Mapper<Employee, co.com.foundation.morphia.entities.Employee> {

	private final Logger LOGGER = LogManager.getLogger(EmployeeMapper.class);

	@Override
	public co.com.foundation.morphia.entities.Employee marshall(Employee input) {
		try {
			LOGGER.info("start -- marshall method");
			EmployeeBuilder builder = co.com.foundation.morphia.entities.Employee.builder();
			return builder.id(input.getId() != null ? new ObjectId(input.getId()) : null)
					.firstName(input.getFirstName()).lastName(input.getLastName())
					.identification(input.getIdentification())
					.hireDate(input.getHireDate() != null ? input.getHireDate() : new Date())
					.phoneNumber(input.getPhoneNumber())
					.email(Email.builder().email(input.getEmail()).password(input.getPassword()).build())
					.department(Department.builder().id(new ObjectId(input.getDepartmentId())).build())
					.job(Job.builder().id(new ObjectId(input.getJobId())).build())
					.manager(input.getManagerId() != "0" ? co.com.foundation.morphia.entities.Employee.builder()
							.id(new ObjectId(input.getManagerId())).build() : null)
					.build();
		} finally {
			LOGGER.info("end -- marshall method");
		}
	}

	@Override
	public Employee unMarshall(co.com.foundation.morphia.entities.Employee input) {
		try {
			LOGGER.info("start -- unMarshall method");
			co.com.foundation.morphia.domain.Employee.EmployeeBuilder builder = Employee.builder();
			builder.id(input.getId().toHexString()).firstName(input.getFirstName()).lastName(input.getLastName())
					.identification(input.getIdentification()).hireDate(input.getHireDate())
					.phoneNumber(input.getPhoneNumber());

			if (input.getEmail() != null) {
				builder.email(input.getEmail().getEmail()).password(input.getEmail().getPassword());
			}

			if (input.getDepartment() != null) {
				builder.departmentId(input.getDepartment().getId().toHexString())
						.departmentName(input.getDepartment().getName());
			}

			if (input.getJob() != null) {
				builder.jobId(input.getJob().getId().toHexString()).jobTittle(input.getJob().getJobTittle());
			}

			if (input.getManager() != null) {
				co.com.foundation.morphia.entities.Employee manager = input.getManager();
				builder.managerId(manager.getId().toHexString())
						.managerName(manager.getFirstName() + " " + manager.getLastName());
			}

			return builder.build();
		} finally {
			LOGGER.info("end -- unMarshall method");
		}
	}

}
