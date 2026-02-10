package sh.surge.kunal.healthnexus.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sh.surge.kunal.healthnexus.dtos.GenericMessage;

@ControllerAdvice // Global exception handler
// Using aspect orientation programming
// Handles exceptions globally
public class GlobalExceptionHandler {

    // Handles PatientNullException
	@ExceptionHandler(PatientNullException.class)
	public ResponseEntity<GenericMessage<String>> handlePatientNullException(PatientNullException ex) {
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new GenericMessage<>(ex.getMessage()));
	}
    // Handles PatientNotFoundException
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<GenericMessage<String>> handlePatientNotFoundException(PatientNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericMessage<>(ex.getMessage()));
    }
    // Handles DoctorNullException
    @ExceptionHandler(DoctorNullException.class)
    public ResponseEntity<GenericMessage<String>> handleDoctorNullException(DoctorNullException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericMessage<>(ex.getMessage()));
    }
    // Handles DoctorNotFoundException
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<GenericMessage<String>> handleDoctorNotFoundException(DoctorNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericMessage<>(ex.getMessage()));
    }
    // Handles AddressNullException
    @ExceptionHandler(AddressNullException.class)
    public ResponseEntity<GenericMessage<String>> handleAddresstNullException(AddressNullException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericMessage<>(ex.getMessage()));
    }
    // Handles AddressNotFoundException
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<GenericMessage<String>> handleAddressNotFoundException(AddressNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new GenericMessage<>(ex.getMessage()));
    }

    // Handles MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class) // Handles validation errors
	public ResponseEntity<GenericMessage<String>>handleArgumentException(MethodArgumentNotValidException ex) {
		Map<String, String> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			 String fieldName = ((FieldError) error).getField().toString();
	         String errorMessage = error.getDefaultMessage();
	         errors.put(fieldName, errorMessage);
	        
		});
		
		response.put("Validation Errors", errors.toString());
		response.put("Total Errors", String.valueOf(errors.size()));
		response.put("TimeStamp", LocalDateTime.now().toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new GenericMessage<>(response.toString()));
	}
}
