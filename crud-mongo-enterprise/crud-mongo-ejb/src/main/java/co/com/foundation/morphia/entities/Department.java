package co.com.foundation.morphia.entities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.Getter;
import lombok.Setter;

@Entity("departments")
@Getter
@Setter
public class Department {

	@Id
	private ObjectId id;
	@Property(value = "name")
	private String name;
	@Reference(value = "manager", lazy = true, ignoreMissing = true)
	private Employee manager;
	@Reference(value = "location", lazy = true, ignoreMissing = true)
	private Locations location;

	public Department() {
		super();
	}
}
