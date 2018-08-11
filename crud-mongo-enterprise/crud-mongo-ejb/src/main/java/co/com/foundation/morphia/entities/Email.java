package co.com.foundation.morphia.entities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import lombok.Getter;
import lombok.Setter;

@Embedded(value = "email")
@Getter
@Setter
public class Email {

	@Id
	private ObjectId id;
	@Property(value = "email")
	private String email;
	@Property(value = "password")
	private String password;

	public Email() {
		super();
	}
}
