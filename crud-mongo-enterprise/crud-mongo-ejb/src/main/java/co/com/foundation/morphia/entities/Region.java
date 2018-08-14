package co.com.foundation.morphia.entities;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(value = "regions")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Region {

	@Id
	private ObjectId id;
	@Property(value = "name")
	private String name;
	@Reference(value = "locations", lazy = true, ignoreMissing = true)
	private List<Object> locations;

	public Region() {
		super();
	}

}
