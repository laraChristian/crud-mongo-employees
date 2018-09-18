package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Region;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionRequest {

	private boolean update;
	private Region region;

	public RegionRequest() {
		super();
	}
}
