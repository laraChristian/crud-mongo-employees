package co.com.foundation.morphia.mapper.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import co.com.foundation.morphia.types.ComponentType;

@Qualifier
@Retention(RUNTIME)
@Target({ FIELD, METHOD, TYPE, PARAMETER })
public @interface Mappers {

	ComponentType type() default ComponentType.REGION;
}
