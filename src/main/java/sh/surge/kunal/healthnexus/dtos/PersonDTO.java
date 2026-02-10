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

public abstract class PersonDTO implements Serializable {
	
	
	
	private FullName fullName;
	
	private Gender gender;

	private LocalDate dateOfBirth;

	private long contactNumber;
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
	private String email;	
 
}
