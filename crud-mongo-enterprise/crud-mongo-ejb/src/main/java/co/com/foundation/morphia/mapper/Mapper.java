package co.com.foundation.morphia.mapper;

public interface Mapper<I, O> {

	O marshall(final I input);

	I unMarshall(final O input);
}
