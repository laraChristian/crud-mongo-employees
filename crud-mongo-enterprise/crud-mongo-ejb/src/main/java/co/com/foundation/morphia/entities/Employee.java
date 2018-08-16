package co.com.foundation.morphia.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.Getter;
import lombok.Setter;

@Entity("employees")
@Getter
@Setter
public class Employee {

	@Id
	private ObjectId id;
	@Property(value = "firstName")
	private String firstName;
	@Property(value = "lastName")
	private String lastName;
	@Embedded(value = "email")
	private Email email;
	@Property(value = "phoneNumber")
	private String phoneNumber;
	@Property(value = "hireDate")
	private Date hireDate;
	@Property(value = "identification")
	private String identification;
	@Reference(value = "manager", lazy = true, ignoreMissing = true)
	private Employee manager;
	@Reference(value = "job", lazy = true, ignoreMissing = true)
	private Job job;
	@Reference(value = "department", lazy = true, ignoreMissing = true)
	private Department department;

	public Employee() {
		super();
	}

	public Employee(ObjectId id, String firstName, String lastName, Email email, String phoneNumber, Date hireDate,
			String identification, Employee manager, Job job, Department department) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.hireDate = hireDate;
		this.identification = identification;
		this.manager = manager;
		this.job = job;
		this.department = department;
	}

}
