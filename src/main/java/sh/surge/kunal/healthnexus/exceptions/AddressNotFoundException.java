package sh.surge.kunal.healthnexus.exceptions;

import java.io.Serializable;

// Exception class for patient not found
public class AddressNotFoundException extends RuntimeException implements Serializable {
    public AddressNotFoundException(String message) {
        // constructor
        super(message);
    }

}
