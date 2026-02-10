package sh.surge.kunal.healthnexus.services;

import java.util.List;

import sh.surge.kunal.healthnexus.models.Patient;

public interface PatientService {
	 // Custom method to add a patient
     Patient addPatient(Patient patient);
    // Custom method to get all patients
     List<Patient> getAllPatients();
     // Get patient by adhaar card no
     Patient getPatientByAdhaarCardNo(String adhaarCardNo);
     // Get patient by phone no
     List<Patient> getPatientByPhoneNumber(long contactNumber);
     // Get patient by email
     Patient getPatientByEmail(String email);
     // Update patient
     Patient updatePatient(String adhaarCardNo, long phoneNumber, String email);
     // Delete patient
     boolean deletePatient(String adhaarCardNo);

}
