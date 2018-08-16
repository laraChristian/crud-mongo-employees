package co.com.foundation.morphia.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Country {

	private String id;
	private String name;
	private String regionId;
	private String regionName;

	public Country() {
		super();
	}

}
