package co.com.foundation.morphia.messages;

import co.com.foundation.morphia.domain.Job;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequest {

	private boolean update;
	private Job job;
}
