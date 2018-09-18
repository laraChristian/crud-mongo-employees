package co.com.foundation.morphia.persistence;

import co.com.foundation.morphia.entities.Country;
import co.com.foundation.morphia.entities.Department;
import co.com.foundation.morphia.entities.Email;
import co.com.foundation.morphia.entities.Employee;
import co.com.foundation.morphia.entities.Job;
import co.com.foundation.morphia.entities.Locations;
import co.com.foundation.morphia.entities.Region;

public interface ConnectionParameters {

	String HOST = "127.0.0.1";
	Integer PORT = 27017;
	String DBNAME = "jsti-employees";
	Class<?> ENTITIES[] = { Region.class, Country.class, Locations.class, Department.class, Job.class, Employee.class,
			Email.class };
}
