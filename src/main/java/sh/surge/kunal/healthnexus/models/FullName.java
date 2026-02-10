package sh.surge.kunal.healthnexus.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
// Embeddable is used to embed the object as a column in the table
public class FullName {
	@Column(name = "first_name", nullable = false,length = 50)
	private String firstName;
	@Column(name = "last_name", nullable = false,length = 50)
	private String lastName;

}
