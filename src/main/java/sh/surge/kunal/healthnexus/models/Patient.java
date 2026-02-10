package sh.surge.kunal.healthnexus.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // Lombok Annotation, used to generate equals and hashcode methods
@SuperBuilder // Lombok Annotation, used to generate builder pattern
@Entity
@Table(name = "patient") // JPA Annotation, used to create table
// Serializable interface is used to make the class serializable i.e. to convert the object into a stream of bytes
public class Patient extends Person implements Serializable {

    @Column(name = "ailment", length = 100, nullable = false)
	private String ailment;	
	@Column(name = "occupation", length = 100, nullable = true)
	private String occupation;

	

}
