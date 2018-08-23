package co.com.foundation.morphia.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.commons.Utils;
import co.com.foundation.morphia.domain.Department;
import co.com.foundation.morphia.entities.Department.DepartmentBuilder;
import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.entities.Locations;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.types.ComponentType;

@ApplicationScoped
@Mappers(type = ComponentType.DEPARTMENT)
public class DepartmentMapper
		implements Mapper<co.com.foundation.morphia.domain.Department, co.com.foundation.morphia.entities.Department> {

	private final Logger LOGGER = LogManager.getLogger(DepartmentMapper.class);

	@Override
	public co.com.foundation.morphia.entities.Department marshall(Department input) {
		try {
			LOGGER.info("start -- map method");
			DepartmentBuilder builder = co.com.foundation.morphia.entities.Department.builder();

			if (input.getId() != null) {
				builder.id(new ObjectId(input.getId()));
			}

			if (!Utils.isToReject.test(input.getManagerId())) {
				builder.manager(Employee.builder().id(new ObjectId(input.getManagerId())).build());
			}

			if (!Utils.isToReject.test(input.getLocationId())) {
				builder.location(Locations.builder().id(new ObjectId(input.getLocationId())).build());
			}

			return builder.name(input.getName()).build();
		} finally {
			LOGGER.info("end -- map method");
		}
	}

	@Override
	public Department unMarshall(co.com.foundation.morphia.entities.Department input) {
		try {
			LOGGER.info("start un-marshall method");
			co.com.foundation.morphia.domain.Department.DepartmentBuilder builder = Department.builder();

			if (input.getLocation() != null) {
				builder.locationId(input.getLocation().getId().toHexString())
						.locationAddress(input.getLocation().getStreetAddress());
			}

			if (input.getManager() != null) {
				builder.managerId(input.getManager().getId().toHexString())
						.managerName(input.getManager().getFirstName() + " " + input.getManager().getLastName());
			}

			return builder.id(input.getId().toHexString()).name(input.getName()).build();
		} finally {
			LOGGER.info("end un-marshall method");
		}
	}

}
