package sh.surge.kunal.healthnexus.dtos;

public record AddressResponse(long id, String streetName, 
		String city, String state, String zipCode, PersonResponse person) {

}
