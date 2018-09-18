package co.com.foundation.morphia.commons;

import java.util.function.Predicate;

public interface Utils {

	enum JOBS {
		PRESIDENT, VICEPRESIDENT, MANAGER
	}

	enum Columns {
		NAME("name"), JOBTITTLE("jobTittle"), JOB("job"), EMAIL("email");

		private Columns(final String id) {
			this.id = id;
		}

		private String id;

		public String getId() {
			return this.id;
		}
	}

	Predicate<String> isToReject = (String id) -> {
		if (id == null) {
			return true;
		}
		return id.trim().equals("0");
	};

}
