package co.com.foundation.morphia.domain;

import java.util.List;

import co.com.foundation.morphia.entities.Employee;
import lombok.Getter;

@Getter
public class EmployeeResponse {

	private boolean success;
	private String message;
	private List<Employee> employees;

	public EmployeeResponse() {
		super();
	}
}
