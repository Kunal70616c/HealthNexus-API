package sh.surge.kunal.healthnexus.services;

import sh.surge.kunal.healthnexus.models.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor addDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Doctor getDoctorByAdhaarId(String adhaarCardNo);
    Doctor getDoctorByEmail(String email);
    Doctor updateDoctor(String adhaarCardNo, String specialization , String qualification);
    boolean deleteDoctor(String adhaarCardNo);
}
