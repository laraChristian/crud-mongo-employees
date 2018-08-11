package co.com.foundation.morphia.entities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.Getter;
import lombok.Setter;

@Entity(value = "locations")
@Getter
@Setter
public class Locations {

	@Id
	private ObjectId id;
	@Property(value = "streetAddress")
	private String streetAddress;
	@Property(value = "postalCode")
	private String postalCode;
	@Property(value = "city")
	private String city;
	@Property(value = "stateProvince")
	private String stateProvince;
	@Reference(value = "country", idOnly = true)
	private Country country;

	public Locations() {
		super();
	}
}
