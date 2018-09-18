package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {

	private boolean update;
	private Employee employee;
}
