package sh.surge.kunal.healthnexus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sh.surge.kunal.healthnexus.models.Patient;

// JpaRepository is used to perform database operations
// Consists all the crud operations
public interface PatientRepository extends JpaRepository<Patient, String> {
    // Optional is used to return null if the patient is not found
    // Custom query is used to find the patient by email
	Optional<Patient> findByEmail(String email);
}
