package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequest {

	private boolean update;
	private Department department;
}
