package sh.surge.kunal.healthnexus.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder

public class PatientDTO extends PersonDTO implements Serializable {
	
	private String ailment;	

	private String occupation;

	

}
