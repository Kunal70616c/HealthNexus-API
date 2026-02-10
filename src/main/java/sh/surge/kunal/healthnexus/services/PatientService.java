package sh.surge.kunal.healthnexus.services;

import java.util.List;

import sh.surge.kunal.healthnexus.models.Patient;

public interface PatientService {
	
     Patient addPatient(Patient patient);
     List<Patient> getAllPatients();
     Patient getPatientByAdhaarCardNo(String adhaarCardNo);
     List<Patient> getPatientByPhoneNumber(long contactNumber);
     Patient getPatientByEmail(String email);
     Patient updatePatient(String adhaarCardNo, long phoneNumber, String email);
     boolean deletePatient(String adhaarCardNo);

}
