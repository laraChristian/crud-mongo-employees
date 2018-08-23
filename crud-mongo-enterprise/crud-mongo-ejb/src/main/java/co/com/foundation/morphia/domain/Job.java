package co.com.foundation.morphia.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Job {

	private String id;
	private String jobTittle;
	private Double minSalary;
	private Double maxSalary;

	public Job() {
		super();
	}

}
