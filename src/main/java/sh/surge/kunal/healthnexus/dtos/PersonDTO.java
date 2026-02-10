package sh.surge.kunal.healthnexus.dtos;
import java.io.Serializable;
import java.time.LocalDate;

import sh.surge.kunal.healthnexus.models.Gender;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor

@SuperBuilder

// DTO is used to transfer data between layers
// It is used to transfer data between the controller and the service layer
// Because Model class can't be exposed to the client
// This is a security measure
public abstract class PersonDTO implements Serializable {

	private FullName fullName;

	private Gender gender;

	private LocalDate dateOfBirth;

	private long contactNumber;
    // Email validation
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
	private String email;	
 
}
