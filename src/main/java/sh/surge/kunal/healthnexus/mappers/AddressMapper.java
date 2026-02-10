package sh.surge.kunal.healthnexus.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import sh.surge.kunal.healthnexus.dtos.AddressDTO;
import sh.surge.kunal.healthnexus.dtos.AddressResponse;
import sh.surge.kunal.healthnexus.models.Address;



@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface AddressMapper {
	
	//dto to entity and entity to dto methods can be defined here
	Address dtotoentity(AddressDTO addressDTO);
    AddressResponse entitytodto(Address address);
    List<AddressResponse> entitytodto(List<Address> addresses);
}
