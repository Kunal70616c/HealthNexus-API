package sh.surge.kunal.healthnexus.exceptions;

import java.io.Serializable;

// Exception class for patient not found
public class PatientNotFoundException extends RuntimeException implements Serializable {
    public PatientNotFoundException(String message) {
        // constructor
        super(message);
    }

}
