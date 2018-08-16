package co.com.foundation.morphia.messages;

import java.util.List;

import co.com.foundation.morphia.domain.Country;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CountryResponse {

	private boolean success;
	private String message;
	private List<Country> countries;
}
