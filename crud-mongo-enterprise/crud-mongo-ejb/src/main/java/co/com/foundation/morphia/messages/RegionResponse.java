package co.com.foundation.morphia.messages;

import java.util.List;

import co.com.foundation.morphia.domain.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionResponse {

	private boolean success;
	private String message;
	private List<Region> regions;

}
