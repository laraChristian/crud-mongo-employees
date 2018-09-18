package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {

	private boolean update;
	private Location location;

}
