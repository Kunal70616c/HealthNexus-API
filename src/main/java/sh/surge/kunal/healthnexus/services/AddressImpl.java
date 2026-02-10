package sh.surge.kunal.healthnexus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.surge.kunal.healthnexus.exceptions.PersonNotFoundException;
import sh.surge.kunal.healthnexus.models.Address;
import sh.surge.kunal.healthnexus.models.Person;
import sh.surge.kunal.healthnexus.repositories.AddressRepository;
import sh.surge.kunal.healthnexus.repositories.PersonRepository;

import java.util.List;

@Service
public class AddressImpl implements AddressService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address addAddress(String adhaarCardNo, Address address) {
        // adds address to the database
        Person person = personRepository.findById(adhaarCardNo) // find person by adhaar card no and throw exception if not found
                .orElseThrow(() -> new PersonNotFoundException("Person not found with Adhaar Card No: " + adhaarCardNo));
        address.setPerson(person); // set person to address
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses(String adhaarCardNo) {
        // returns all addresses of a person
        return addressRepository.findByPersonAdhaarCardNo(adhaarCardNo);
    }

    @Override
    public Address getAddressById(String adhaarCardNo, Long addressId) {
        // returns address by id and person adhaar card no
        return addressRepository.findByIdAndPersonAdhaarCardNo(addressId, adhaarCardNo).orElseThrow(() -> new RuntimeException("Address not found"));
    }

    @Override
    public Address updateAddress(String adhaarCardNo, Long addressId, Address address) {
        // updates address by id and person adhaar card no
        Person person = personRepository.findById(adhaarCardNo)
                .orElseThrow(() -> new PersonNotFoundException
                        ("Person not found with Adhaar Card No: " + adhaarCardNo));
        Address existingAddress =
                addressRepository.findByIdAndPersonAdhaarCardNo(addressId, adhaarCardNo).orElseThrow(() -> new RuntimeException("Address not found"));
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
        boolean isDeleted = false;
        // checks if person exists and address exists
        if (personRepository.existsById(adhaarCardNo) &&
                addressRepository.existsByIdAndPersonAdhaarCardNo(addressId, adhaarCardNo)) {
            addressRepository.deleteById(addressId);
            isDeleted = true;
        }
        return isDeleted; // Returns according to result
    }

}
