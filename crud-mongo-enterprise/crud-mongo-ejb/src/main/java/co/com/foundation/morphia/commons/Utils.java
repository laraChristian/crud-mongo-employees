package co.com.foundation.morphia.commons;

import java.util.function.Predicate;

public interface Utils {

	enum JOBS {
		PRESIDENT, MANAGER
	}

	Predicate<String> isToReject = (String id) -> {
		if (id == null) {
			return true;
		}
		return id.trim().equals("0");
	};
}
