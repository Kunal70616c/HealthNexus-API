package sh.surge.kunal.healthnexus.exceptions;

import java.io.Serializable;

// Exception class for patient not found
public class DoctorNotFoundException extends RuntimeException implements Serializable {
    public DoctorNotFoundException(String message) {
        // constructor
        super(message);
    }

}
