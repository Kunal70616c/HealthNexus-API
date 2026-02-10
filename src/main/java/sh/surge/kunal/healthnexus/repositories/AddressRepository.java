package sh.surge.kunal.healthnexus.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sh.surge.kunal.healthnexus.models.Address;
// JpaRepository is used to perform database operations
// Consists all the crud operations
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Custom query is used to find the address by person adhaar card no
	List<Address> findByPersonAdhaarCardNo(String adhaarCardNo);
	//method name matters, spring jpa will create query based on method name
    // This Method name is used to find the address by id and person adhaar card no
	 Optional<Address> findByIdAndPersonAdhaarCardNo(Long id, String adhaarCardNo);
	//method name matters, spring jpa will create query based on method name
	 boolean existsByIdAndPersonAdhaarCardNo(Long id, String adhaarCardNo);
}
