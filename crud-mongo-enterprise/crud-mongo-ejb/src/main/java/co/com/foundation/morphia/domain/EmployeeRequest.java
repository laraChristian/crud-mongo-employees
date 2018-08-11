package co.com.foundation.morphia.domain;

import co.com.foundation.morphia.entities.Employee;
import lombok.Getter;

@Getter
public class EmployeeRequest {

	private boolean update;
	private Employee employee;

	public EmployeeRequest() {
		super();
	}
}
