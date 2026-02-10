package sh.surge.kunal.healthnexus.dtos;

import sh.surge.kunal.healthnexus.models.Gender;

import java.time.LocalDate;

// Response class for doctor
// It is a record class,meaning its immutable
public record DoctorResponse(String adhaarCardNo,
                             FullNameResponse fullNameResponse, String email,
                             Gender gender,
                             LocalDate dob,
                             long contactNo,
                             String licenseNumber,
                             String specialization,
                             String qualification) {

}
