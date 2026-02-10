package sh.surge.kunal.healthnexus.services;

import java.util.List;

import sh.surge.kunal.healthnexus.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sh.surge.kunal.healthnexus.models.Address;
import sh.surge.kunal.healthnexus.models.Person;
import sh.surge.kunal.healthnexus.repositories.AddressRepository;
import sh.surge.kunal.healthnexus.repositories.PersonRepository;

@Service
public class AddressImpl implements AddressService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address addAddress(String adhaarCardNo, Address address) {
		// TODO Auto-generated method stub
		Person person = personRepository.findById(adhaarCardNo)
				.orElseThrow(()->new PersonNotFoundException("Person not found with Adhaar Card No: " + adhaarCardNo));
		address.setPerson(person);
		return addressRepository.save(address);		
	}

	@Override
	public List<Address> getAllAddresses(String adhaarCardNo) {
		// TODO Auto-generated method stub
		return addressRepository.findByPersonAdhaarCardNo(adhaarCardNo);
	}

	@Override
	public Address getAddressById(String adhaarCardNo, Long addressId) {
		// TODO Auto-generated method stub
		return addressRepository.findByIdAndPersonAdhaarCardNo(addressId,adhaarCardNo).orElseThrow(()->new RuntimeException("Address not found"));
	}

	@Override
	public Address updateAddress(String adhaarCardNo, Long addressId, Address address) {
		// TODO Auto-generated method stub
		Person person = personRepository.findById(adhaarCardNo)
				.orElseThrow(()->new PersonNotFoundException
						("Person not found with Adhaar Card No: " + adhaarCardNo));
	     Address existingAddress = 
				addressRepository.findByIdAndPersonAdhaarCardNo(addressId,adhaarCardNo).orElseThrow(()->new RuntimeException("Address not found"));
		existingAddress.setStreetName(address.getStreetName());
		existingAddress.setCity(address.getCity());
		existingAddress.setState(address.getState());
		existingAddress.setZipCode(address.getZipCode());
		existingAddress.setHouseNumber(address.getHouseNumber());
		existingAddress.setPerson(person);
		return addressRepository.save(existingAddress);
		
	}

	@Override
	public boolean deleteAddress(String adhaarCardNo, Long addressId) {
		// TODO Auto-generated method stub
		return personRepository.existsById(adhaarCardNo) && 
				addressRepository.existsByIdAndPersonAdhaarCardNo(addressId, adhaarCardNo);
		
	}

}
