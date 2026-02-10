package sh.surge.kunal.healthnexus.dtos;

import java.time.LocalDate;

import sh.surge.kunal.healthnexus.models.Gender;
// Response class for patient
// It is a record class,meaning its immutable
public record PatientResponse(String adhaarCardNo, 
		FullNameResponse fullNameResponse,String email,
		Gender gender,LocalDate dob,long 
		contactNo,String ailment,String occupation) {

}
