package sh.surge.kunal.healthnexus.mappers;

import org.mapstruct.Mapper;

import sh.surge.kunal.healthnexus.dtos.FullNameResponse;
import sh.surge.kunal.healthnexus.models.FullName;

@Mapper(componentModel = "spring")
public interface FullNameMapper {

	FullNameResponse toDTOs(FullName fullName);
}
