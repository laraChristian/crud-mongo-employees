package co.com.foundation.morphia.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Employee {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
	private Date hireDate;
	private String identification;
	private String managerId;
	private String managerName;
	private String jobId;
	private String jobTittle;
	private String departmentId;
	private String departmentName;

	public Employee() {
		super();
	}

}
