package co.com.foundation.morphia.entities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import lombok.Getter;
import lombok.Setter;

@Entity(value = "jobs")
@Getter
@Setter
public class Job {

	@Id
	private ObjectId id;
	@Property(value = "jobTittle")
	private String jobTittle;
	@Property(value = "minSalary")
	private Double minSalary;
	@Property(value = "maxSalary")
	private Double maxSalary;

	public Job() {
		super();
	}
}
