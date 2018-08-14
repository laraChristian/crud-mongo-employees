package co.com.foundation.morphia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Region {

	private String id;
	private String name;

	public Region() {
		super();
	}
}
