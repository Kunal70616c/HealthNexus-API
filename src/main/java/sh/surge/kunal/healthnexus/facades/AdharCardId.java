package sh.surge.kunal.healthnexus.facades;

import java.lang.annotation.Target;

import org.hibernate.annotations.IdGeneratorType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import sh.surge.kunal.healthnexus.models.AdhaarCardIdGenerator;

@IdGeneratorType(AdhaarCardIdGenerator.class)
@Target({ ElementType.FIELD, ElementType.METHOD,
		ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdhaarCardId {

}
