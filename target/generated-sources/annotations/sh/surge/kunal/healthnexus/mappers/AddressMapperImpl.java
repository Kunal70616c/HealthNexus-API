package sh.surge.kunal.healthnexus.mappers;

import sh.surge.kunal.healthnexus.dtos.AddressDTO;
import sh.surge.kunal.healthnexus.dtos.AddressResponse;
import sh.surge.kunal.healthnexus.dtos.PersonResponse;
import sh.surge.kunal.healthnexus.models.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-30T00:28:02+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Homebrew)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public Address dtotoentity(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setHouseNumber( addressDTO.getHouseNumber() );
        address.setStreetName( addressDTO.getStreetName() );
        address.setCity( addressDTO.getCity() );
        address.setState( addressDTO.getState() );
        address.setZipCode( addressDTO.getZipCode() );

        return address;
    }

    @Override
    public AddressResponse entitytodto(Address address) {
        if ( address == null ) {
            return null;
        }

        long id = 0L;
        String streetName = null;
        String city = null;
        String state = null;
        String zipCode = null;
        PersonResponse person = null;

        if ( address.getId() != null ) {
            id = address.getId();
        }
        streetName = address.getStreetName();
        city = address.getCity();
        state = address.getState();
        zipCode = address.getZipCode();
        person = personMapper.entityToDto( address.getPerson() );

        AddressResponse addressResponse = new AddressResponse( id, streetName, city, state, zipCode, person );

        return addressResponse;
    }

    @Override
    public List<AddressResponse> entitytodto(List<Address> addresses) {
        if ( addresses == null ) {
            return null;
        }

        List<AddressResponse> list = new ArrayList<AddressResponse>( addresses.size() );
        for ( Address address : addresses ) {
            list.add( entitytodto( address ) );
        }

        return list;
    }
}
