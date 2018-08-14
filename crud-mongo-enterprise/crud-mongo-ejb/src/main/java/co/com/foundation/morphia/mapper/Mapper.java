package co.com.foundation.morphia.mapper;

public interface Mapper<I, O> {

	O map(final I input);
}
