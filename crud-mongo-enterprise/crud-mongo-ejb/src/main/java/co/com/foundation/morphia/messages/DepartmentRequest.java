package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Department;
import lombok.Getter;

@Getter
public class DepartmentRequest {

	private boolean update;
	private Department department;
}
