package sh.surge.kunal.healthnexus.models;

import java.util.EnumSet;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import com.github.javafaker.Faker;
 // BeforeExecutionGenerator is used to generate id before insert
public class AdhaarCardIdGenerator implements BeforeExecutionGenerator {

	@Override
	public EnumSet<EventType> getEventTypes() {
		// EventType.INSERT is used to generate id before insert
		return EnumSet.of(EventType.INSERT);
	}

	@Override
    // SharedSessionContractImplementor is used to get the session
    // owner is the object that is being inserted
    // currentValue is the current value of the id
    // eventType is the type of event
	public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
			EventType eventType) {
		Faker faker = new Faker(); // Faker is used to generate random numbers

		if (currentValue != null) {
			return currentValue;
		}else {
			return faker.number().digits(12); // digits(12) is used to generate 12 digit numbers
		}
	}
	

}
