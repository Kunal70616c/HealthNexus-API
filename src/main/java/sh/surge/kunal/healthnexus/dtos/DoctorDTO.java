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
// DoctorDTO extends PersonDTO
// It is used to transfer data between the controller and the service layer
// Because Model class can't be exposed to the client
// This is a security measure
public class DoctorDTO extends PersonDTO implements Serializable {	
	private String licenseNumber;	
	private String specialization;		
	private String qualification;

}
