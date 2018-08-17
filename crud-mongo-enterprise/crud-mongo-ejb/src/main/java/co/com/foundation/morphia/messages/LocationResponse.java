package co.com.foundation.morphia.messages;

import java.util.List;

import co.com.foundation.morphia.domain.Location;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationResponse {

	private boolean success;
	private String message;
	private List<Location> locations;
}
