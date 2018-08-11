package co.com.foundation.morphia.domain;

import co.com.foundation.morphia.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponse {

	private boolean success;
	private String message;
	private User user;

	LoginResponse() {
		super();
	}

}
