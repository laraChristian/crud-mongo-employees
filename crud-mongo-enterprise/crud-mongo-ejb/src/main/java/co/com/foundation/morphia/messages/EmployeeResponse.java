package co.com.foundation.morphia.messages;

import java.util.List;

import co.com.foundation.morphia.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeResponse {

	private boolean success;
	private String message;
	private List<Employee> employees;

	public EmployeeResponse() {
		super();
	}
}
