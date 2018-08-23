package co.com.foundation.morphia.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Department {

	private String id;
	private String name;
	private String managerId;
	private String managerName;
	private String locationId;
	private String locationAddress;

	public Department() {
		super();
	}

}
