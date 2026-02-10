package sh.surge.kunal.healthnexus.exceptions;

import java.io.Serializable;
// Exception class for patient null
public class PatientNullException extends RuntimeException implements Serializable {
	public PatientNullException(String message) {
        // constructor
        super(message);
	}

}
