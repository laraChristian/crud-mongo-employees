package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Country;
import lombok.Getter;

@Getter
public class CountryRequest {

	private boolean update;
	private Country country;

}
