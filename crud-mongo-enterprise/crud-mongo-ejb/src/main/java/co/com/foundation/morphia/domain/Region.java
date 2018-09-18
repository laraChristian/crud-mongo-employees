package co.com.foundation.morphia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Region {

	private String id;
	private String name;

	public Region() {
		super();
	}

	public Region(String id) {
		super();
		this.id = id;
	}

}
