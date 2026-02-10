package sh.surge.kunal.healthnexus.dtos;

import java.time.LocalDate;

import sh.surge.kunal.healthnexus.models.Gender;
// Response class for person
// It is a record class,meaning its immutable
public record PersonResponse(String adhaarCardNo, 
		FullNameResponse fullNameResponse,String email,
		Gender gender,LocalDate dob,long 
		contactNo) {

}
