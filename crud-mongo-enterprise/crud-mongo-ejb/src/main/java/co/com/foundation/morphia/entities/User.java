package co.com.foundation.morphia.entities;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private ObjectId id;
	private String userName;
	private String password;

	public User() {
		super();
	}
}
