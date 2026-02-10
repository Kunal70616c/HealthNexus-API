package sh.surge.kunal.healthnexus.mappers;

import sh.surge.kunal.healthnexus.dtos.FullNameResponse;
import sh.surge.kunal.healthnexus.dtos.PersonResponse;
import sh.surge.kunal.healthnexus.models.Gender;
import sh.surge.kunal.healthnexus.models.Person;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-30T00:28:02+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Homebrew)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Autowired
    private FullNameMapper fullNameMapper;

    @Override
    public PersonResponse entityToDto(Person person) {
        if ( person == null ) {
            return null;
        }

        FullNameResponse fullNameResponse = null;
        long contactNo = 0L;
        LocalDate dob = null;
        String adhaarCardNo = null;
        String email = null;
        Gender gender = null;

        fullNameResponse = fullNameMapper.toDTOs( person.getFullName() );
        contactNo = person.getContactNumber();
        dob = person.getDateOfBirth();
        adhaarCardNo = person.getAdhaarCardNo();
        email = person.getEmail();
        gender = person.getGender();

        PersonResponse personResponse = new PersonResponse( adhaarCardNo, fullNameResponse, email, gender, dob, contactNo );

        return personResponse;
    }
}
