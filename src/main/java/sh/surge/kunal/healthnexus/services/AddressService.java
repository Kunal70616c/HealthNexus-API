package sh.surge.kunal.healthnexus.services;

import java.util.List;

import sh.surge.kunal.healthnexus.models.Address;

public interface AddressService {
	
	Address addAddress(String adhaarCardNo, Address address);
	List<Address> getAllAddresses(String adhaarCardNo);
	Address getAddressById(String adhaarCardNo, Long addressId);
	Address updateAddress(String adhaarCardNo, Long addressId, Address address);
	boolean deleteAddress(String adhaarCardNo, Long addressId);

}
