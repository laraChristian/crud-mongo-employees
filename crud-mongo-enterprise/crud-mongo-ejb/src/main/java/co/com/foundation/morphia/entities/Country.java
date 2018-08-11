package co.com.foundation.morphia.entities;

import javax.persistence.Entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Country {

	@Id
	private ObjectId id;
	@Property(value = "countryName")
	private String countryName;
	@Reference(idOnly = true)
	@Property(value = "region")
	private Region region;

	public Country() {
		super();
	}
}
