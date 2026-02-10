package sh.surge.kunal.healthnexus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sh.surge.kunal.healthnexus.models.Patient;

// JpaRepository is used to perform database operations
// Consists all the crud operations
public interface PatientRepository extends JpaRepository<Patient, String> {

	Optional<Patient> findByEmail(String email);
}
