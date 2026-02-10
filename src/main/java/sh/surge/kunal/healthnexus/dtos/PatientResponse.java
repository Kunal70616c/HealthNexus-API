package sh.surge.kunal.healthnexus.dtos;

import java.time.LocalDate;

import sh.surge.kunal.healthnexus.models.Gender;

public record PatientResponse(String adhaarCardNo, 
		FullNameResponse fullNameResponse,String email,
		Gender gender,LocalDate dob,long 
		contactNo,String ailment,String occupation) {

}
