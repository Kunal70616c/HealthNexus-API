package sh.surge.kunal.healthnexus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sh.surge.kunal.healthnexus.exceptions.PatientNotFoundException;
import sh.surge.kunal.healthnexus.exceptions.PatientNullException;
import sh.surge.kunal.healthnexus.models.Patient;
import sh.surge.kunal.healthnexus.repositories.PatientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service // marks the class as a service component
@Transactional(readOnly = true) //  Makes the class transactional
// readOnly = true means that transaction does not apply on read operations
public class PatientServiceImpl implements PatientService {
    @Autowired 
	private PatientRepository patientRepository; // PatientRepository is used to perform database operations
    @Autowired
    private EntityManager entityManager; // EntityManager is used to perform database operations , specifically for criteria queries
	@Override
	@Transactional(propagation = Propagation.SUPPORTS,timeout = 100, rollbackFor = PatientNullException.class)
	// propagation = Propagation.SUPPORTS means that transaction does not apply on read operations
	// timeout = 100 means that transaction will be rolled back if it takes more than 100 seconds
	// rollbackFor = PatientNullException.class means that transaction will be rolled back if PatientNullException is thrown
	public Patient addPatient(Patient patient) {
		if(patient!=null) {
			return patientRepository.save(patient);
		}else
			throw new PatientNullException("Patient object is null"); // throws PatientNullException if patient is null
		
	}

	@Override
	public List<Patient> getAllPatients() {
		// returns all patients from the database
		return patientRepository.findAll();
	}

	@Override
	public Patient getPatientByAdhaarCardNo(String adhaarCardNo) {
		// returns patient by adhaar card no
		return patientRepository.findById(adhaarCardNo)
				.orElseThrow(()->
				new PatientNotFoundException("Patient not found in the "
						+ "database with Adhaar Card No: "+adhaarCardNo));
	}

	@Override
	public List<Patient> getPatientByPhoneNumber(long contactNumber) {
		// returns patient by phone number
        // using criteria query to get patient by phone number
        // CriteriaBuilder is used to build criteria queries, cause phone number is not unique
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		Root<Patient> patient = cq.from(Patient.class);
		cq.select(patient).where(cb.equal(patient.get("contactNumber"), contactNumber));
		return entityManager.createQuery(cq).getResultList();
		
	}

	@Override
	@Transactional(rollbackFor = PatientNotFoundException.class)
    // rollbackFor = PatientNotFoundException.class means that transaction will be rolled back if PatientNotFoundException is thrown
	public Patient updatePatient(String adhaarCardNo, long phoneNumber, String email) {
		// updates patient by adhaar card no
		if(patientRepository.existsById(adhaarCardNo) && email!=null && phoneNumber!=0) {
			Patient patient=patientRepository.findById(adhaarCardNo).get();
			patient.setContactNumber(phoneNumber);
			patient.setEmail(email);
			return patientRepository.save(patient);
		}else
			throw new PatientNotFoundException("Patient not found to update");
	}

	@Override
	@Transactional // transaction is required for delete operation
	public boolean deletePatient(String adhaarCardNo) {
		boolean status=false;
		// deletes patient by adhaar card no
		if(patientRepository.existsById(adhaarCardNo)) {
			patientRepository.deleteById(adhaarCardNo);
			status=true;
		}
		return status;
	}

	@Override
	public Patient getPatientByEmail(String email) {
		// returns patient by email
		return patientRepository.findByEmail(email)
				.orElseThrow(()->new 
						PatientNotFoundException("Patient not found "
								+ "with email: "+email));
	}

}
