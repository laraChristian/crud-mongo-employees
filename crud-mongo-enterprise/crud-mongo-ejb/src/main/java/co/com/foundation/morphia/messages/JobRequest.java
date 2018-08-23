package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Job;
import lombok.Getter;

@Getter
public class JobRequest {

	private boolean update;
	private Job job;
}
