package sh.surge.kunal.healthnexus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sh.surge.kunal.healthnexus.dtos.PersonResponse;
import sh.surge.kunal.healthnexus.models.Person;

@Mapper(componentModel = "spring", uses = {FullNameMapper.class})
public interface PersonMapper {
	//dto to entity and entity to dto methods can be defined here
	@Mapping(target = "fullNameResponse", source = "fullName")
	@Mapping(target = "contactNo", source = "contactNumber")
	@Mapping(target="dob", source="dateOfBirth")
	PersonResponse entityToDto(Person person);

}
