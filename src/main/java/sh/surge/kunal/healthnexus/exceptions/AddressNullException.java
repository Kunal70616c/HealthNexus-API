package sh.surge.kunal.healthnexus.exceptions;

import java.io.Serializable;

// Exception class for patient null
public class AddressNullException extends RuntimeException implements Serializable {
	public AddressNullException(String message) {
        // constructor
        super(message);
	}

}
