package sh.surge.kunal.healthnexus.services;

import java.util.List;

import sh.surge.kunal.healthnexus.models.Address;

public interface AddressService {

    // Add Address
	Address addAddress(String adhaarCardNo, Address address);
    // Get address
	List<Address> getAllAddresses(String adhaarCardNo);
	// Get address by id
    Address getAddressById(String adhaarCardNo, Long addressId);
	// Update Address
    Address updateAddress(String adhaarCardNo, Long addressId, Address address);
	// Delete Address
    boolean deleteAddress(String adhaarCardNo, Long addressId);

}
