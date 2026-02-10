package sh.surge.kunal.healthnexus.facades;

import org.hibernate.annotations.IdGeneratorType;
import sh.surge.kunal.healthnexus.models.AdhaarCardIdGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IdGeneratorType(AdhaarCardIdGenerator.class) // IdGeneratorType is used to specify the id generator
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER}) // Target is Field, Method, Parameter
// Target is used to specify where the annotation can be used
@Retention(RetentionPolicy.RUNTIME) // Retention Policy is Runtime meaning it will be available at runtime
// This is a custom annotation for adhaar card id
public @interface AdhaarCardId {

}
