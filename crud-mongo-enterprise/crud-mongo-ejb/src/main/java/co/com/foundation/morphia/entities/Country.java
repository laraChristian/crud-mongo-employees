package co.com.foundation.morphia.entities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.Getter;
import lombok.Setter;

@Entity("countries")
@Getter
@Setter
public class Country {

	@Id
	private ObjectId id;
	@Property(value = "countryName")
	private String countryName;
	@Reference(value = "region", lazy = true, ignoreMissing = true)
	private Region region;

	public Country() {
		super();
	}
}
