package sh.surge.kunal.healthnexus.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sh.surge.kunal.healthnexus.dtos.PatientResponse;
import sh.surge.kunal.healthnexus.models.Patient;

@Mapper(componentModel = "spring", uses = {FullNameMapper.class})
public interface PatientMapper {
	@Mapping(source = "fullName", target = "fullNameResponse")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "contactNumber", target = "contactNo")
		PatientResponse toDTOs(Patient patient);
		List<PatientResponse> toDTOs(List<Patient> patients);
		
		//Patient toObject(PatientDTO patientDTO);

}
