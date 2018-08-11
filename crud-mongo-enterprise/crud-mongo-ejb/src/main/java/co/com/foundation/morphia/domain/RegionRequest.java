package co.com.foundation.morphia.domain;

import co.com.foundation.morphia.entities.Region;
import lombok.Getter;

@Getter
public class RegionRequest {

	private boolean update;
	private Region region;

	public RegionRequest() {
		super();
	}
}
