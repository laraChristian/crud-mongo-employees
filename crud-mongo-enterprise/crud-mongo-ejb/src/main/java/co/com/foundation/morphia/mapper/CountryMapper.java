package co.com.foundation.morphia.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Country;
import co.com.foundation.morphia.entities.Country.CountryBuilder;
import co.com.foundation.morphia.entities.Region;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.types.ComponentType;

@ApplicationScoped
@Mappers(type = ComponentType.COUNTRY)
public class CountryMapper
		implements Mapper<co.com.foundation.morphia.domain.Country, co.com.foundation.morphia.entities.Country> {

	private final Logger LOGGER = LogManager.getLogger(CountryMapper.class);

	@Override
	public co.com.foundation.morphia.entities.Country map(Country input) {
		try {
			LOGGER.info("start -- map method");
			CountryBuilder builder = co.com.foundation.morphia.entities.Country.builder();
			if (input.getId() != null) {
				builder.id(new ObjectId(input.getId()));
			}

			return builder.countryName(input.getName()).region(new Region(new ObjectId(input.getRegionId()))).build();
		} finally {
			LOGGER.info("end -- map method");
		}
	}

}
