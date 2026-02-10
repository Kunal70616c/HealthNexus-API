package sh.surge.kunal.healthnexus.models;
import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import sh.surge.kunal.healthnexus.facades.AdhaarCardId;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // JPA Annotation
@Table(name = "person") // Table Annotation , name of the table
@SuperBuilder // Lombok Annotation , used to create builder pattern
@Inheritance(strategy = InheritanceType.JOINED) // JPA Annotation , used to create inheritance
// Joined is a strategy where each table has its own primary key
public abstract class Person implements Serializable {
	@Id // JPA Annotation , used to create primary key
	@Column(name = "adhaar_card_no", nullable = false, length = 12)
	@AdhaarCardId // Custom Annotation
	@Schema(hidden = true) // Swagger Annotation, used for hiding the field
	private String adhaarCardNo;
	@Embedded // JPA Annotation, used to embed the object as a column in the table
	private FullName fullName;
	@Enumerated(EnumType.STRING) // JPA Annotation, used to store the enum as a string in the database
	@Column(name = "gender", nullable = false,length = 10)
	private Gender gender;
	@Column(name = "date_of_birth", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Spring Annotation, used to format the date
	private LocalDate dateOfBirth;
	@Column(name = "contact_number")
	private long contactNumber;
	@Column(name = "email", length = 150, unique = true,nullable = true)
	private String email;	
 
}
