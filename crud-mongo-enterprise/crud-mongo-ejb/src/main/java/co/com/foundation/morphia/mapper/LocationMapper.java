package co.com.foundation.morphia.mapper;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;

import co.com.foundation.morphia.domain.Location;
import co.com.foundation.morphia.entities.Country;
import co.com.foundation.morphia.entities.Locations;
import co.com.foundation.morphia.entities.Locations.LocationsBuilder;
import co.com.foundation.morphia.mapper.annotation.Mappers;
import co.com.foundation.morphia.types.ComponentType;

@ApplicationScoped
@Mappers(type = ComponentType.LOCATION)
public class LocationMapper implements Mapper<Location, Locations> {

	private final Logger LOGGER = LogManager.getLogger(LocationMapper.class);

	@Override
	public Locations map(Location input) {
		try {
			LOGGER.info("start -- map method");
			LocationsBuilder builder = Locations.builder();
			if (input.getId() != null) {
				builder.id(new ObjectId(input.getId()));
			}

			return builder.streetAddress(input.getStreetAddress()).postalCode(input.getPostalCode())
					.city(input.getCity()).stateProvince(input.getStateProvince())
					.country(new Country(new ObjectId(input.getCountryId()))).build();
		} finally {
			LOGGER.info("end -- map method");
		}
	}

}
