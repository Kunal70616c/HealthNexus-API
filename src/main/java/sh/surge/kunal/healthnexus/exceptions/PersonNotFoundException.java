package sh.surge.kunal.healthnexus.exceptions;
// Exception class for person not found
public class PersonNotFoundException extends RuntimeException {
	public PersonNotFoundException(String message) {
		// constructor
        super(message);
	}

}
