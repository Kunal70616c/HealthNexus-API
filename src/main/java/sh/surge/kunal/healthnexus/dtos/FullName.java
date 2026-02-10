package sh.surge.kunal.healthnexus.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// FullName class is used to store the full name of a person
public class FullName {
	@NotNull // Validates Can't be null
	@NotEmpty // Validates Can't be empty
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only alphabetic characters")
	private String firstName;
	@NotNull
	@NotEmpty
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only alphabetic characters")
	private String lastName;
}
