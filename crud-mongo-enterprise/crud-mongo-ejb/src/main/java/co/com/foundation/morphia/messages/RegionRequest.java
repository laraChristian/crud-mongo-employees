package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Region;
import lombok.Getter;

@Getter
public class RegionRequest {

	private boolean update;
	private Region region;

	public RegionRequest() {
		super();
	}
}
