package co.com.foundation.morphia.messages;

import java.util.List;

import co.com.foundation.morphia.domain.Department;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepartmentResponse {

	private boolean success;
	private String message;
	private List<Department> departments;
}
