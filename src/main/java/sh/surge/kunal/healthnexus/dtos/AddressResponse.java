package sh.surge.kunal.healthnexus.dtos;
// Response class for address
// It is a record class,meaning its immutable
public record AddressResponse(long id, String streetName, 
		String city, String state, String zipCode, PersonResponse person) {

}
