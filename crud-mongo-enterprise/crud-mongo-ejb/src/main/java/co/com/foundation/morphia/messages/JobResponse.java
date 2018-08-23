package co.com.foundation.morphia.messages;

import java.util.List;

import co.com.foundation.morphia.domain.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JobResponse {

	private boolean success;
	private String message;
	private List<Job> jobs;

	public JobResponse() {
		super();
	}

}
