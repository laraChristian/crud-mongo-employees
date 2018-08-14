package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	private User user;

}
