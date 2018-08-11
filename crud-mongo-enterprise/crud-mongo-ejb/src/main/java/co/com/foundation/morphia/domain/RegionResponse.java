package co.com.foundation.morphia.domain;

import java.util.List;

import co.com.foundation.morphia.entities.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionResponse {

	private boolean success;
	private String message;
	private List<Region> regions;
}
