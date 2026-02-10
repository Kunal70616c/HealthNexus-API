package sh.surge.kunal.healthnexus.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
// This is a generic class used for sending messages via exception handling
public class GenericMessage<T> {
	private T object;
	private String message;
	// Constructor for object
	public GenericMessage(T object) {
		super();
		this.object = object;
		
	}
	// Constructor for message
	public GenericMessage(String message) {
		super();
		this.message = message;
		
	}
}
