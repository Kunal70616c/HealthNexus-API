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

@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {
    @Autowired 
	private PatientRepository patientRepository;
    @Autowired
    private EntityManager entityManager;
	@Override
	@Transactional(propagation = Propagation.SUPPORTS,timeout = 100, rollbackFor = PatientNullException.class)
	public Patient addPatient(Patient patient) {
		// TODO Auto-generated method stub
		if(patient!=null) {
			return patientRepository.save(patient);
		}else
			throw new PatientNullException("Patient object is null");
		
	}

	@Override
	public List<Patient> getAllPatients() {
		// TODO Auto-generated method stub
		return patientRepository.findAll();
	}

	@Override
	public Patient getPatientByAdhaarCardNo(String adhaarCardNo) {
		// TODO Auto-generated method stub
		return patientRepository.findById(adhaarCardNo)
				.orElseThrow(()->
				new PatientNotFoundException("Patient not found in the "
						+ "database with Adhaar Card No: "+adhaarCardNo));
	}

	@Override
	public List<Patient> getPatientByPhoneNumber(long contactNumber) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		Root<Patient> patient = cq.from(Patient.class);
		cq.select(patient).where(cb.equal(patient.get("contactNumber"), contactNumber));
		return entityManager.createQuery(cq).getResultList();
		
	}

	@Override
	@Transactional(rollbackFor = PatientNotFoundException.class)
	public Patient updatePatient(String adhaarCardNo, long phoneNumber, String email) {
		// TODO Auto-generated method stub
		if(patientRepository.existsById(adhaarCardNo) && email!=null && phoneNumber!=0) {
			Patient patient=patientRepository.findById(adhaarCardNo).get();
			patient.setContactNumber(phoneNumber);
			patient.setEmail(email);
			return patientRepository.save(patient);
		}else
			throw new PatientNotFoundException("Patient not found to update");
	}

	@Override
	@Transactional
	public boolean deletePatient(String adhaarCardNo) {
		boolean status=false;
		// TODO Auto-generated method stub
		if(patientRepository.existsById(adhaarCardNo)) {
			patientRepository.deleteById(adhaarCardNo);
			status=true;
		}
		return status;
	}

	@Override
	public Patient getPatientByEmail(String email) {
		// TODO Auto-generated method stub
		return patientRepository.findByEmail(email)
				.orElseThrow(()->new 
						PatientNotFoundException("Patient not found "
								+ "with email: "+email));
	}

}
