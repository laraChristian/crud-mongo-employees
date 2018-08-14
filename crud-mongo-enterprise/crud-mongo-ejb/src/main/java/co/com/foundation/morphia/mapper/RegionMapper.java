package co.com.foundation.morphia.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Region;
import co.com.foundation.morphia.entities.Region.RegionBuilder;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.types.ComponentType;

@ApplicationScoped
@Mappers(type = ComponentType.REGION)
public class RegionMapper
		implements Mapper<co.com.foundation.morphia.domain.Region, co.com.foundation.morphia.entities.Region> {

	private final Logger LOGGER = LogManager.getLogger(RegionMapper.class);

	@Override
	public co.com.foundation.morphia.entities.Region map(Region input) {
		try {
			LOGGER.info("start -- map method");
			RegionBuilder builder = co.com.foundation.morphia.entities.Region.builder();

			if (input.getId() != null) {
				builder.id(new ObjectId(input.getId()));
			}

			return builder.name(input.getName()).build();
		} finally {
			LOGGER.info("end -- map method");
		}
	}

}
