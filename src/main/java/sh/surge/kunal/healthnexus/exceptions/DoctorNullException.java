package sh.surge.kunal.healthnexus.exceptions;

import java.io.Serializable;

// Exception class for patient null
public class DoctorNullException extends RuntimeException implements Serializable {
	public DoctorNullException(String message) {
        // constructor
        super(message);
	}

}
