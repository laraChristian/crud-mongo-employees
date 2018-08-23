package co.com.foundation.morphia.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity("employees")
@Getter
@Setter
@Builder
@AllArgsConstructor
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

}
