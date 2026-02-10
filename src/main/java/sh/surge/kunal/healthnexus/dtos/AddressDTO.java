package sh.surge.kunal.healthnexus.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// AddressDTO is used to transfer data between the controller and the service layer
// Because Model class can't be exposed to the client
// This is a security measure
public class AddressDTO {
	
	
	
	private String houseNumber;
	
	private String streetName;

	private String city;

	private String state;
	
	private String zipCode;
	//private PersonDTO person;

}
