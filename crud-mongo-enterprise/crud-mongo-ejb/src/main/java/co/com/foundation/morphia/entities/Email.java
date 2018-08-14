package co.com.foundation.morphia.entities;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import lombok.Getter;
import lombok.Setter;

@Embedded
@Getter
@Setter
public class Email {

	@Property(value = "email")
	private String email;
	@Property(value = "password")
	private String password;

	public Email() {
		super();
	}
}
