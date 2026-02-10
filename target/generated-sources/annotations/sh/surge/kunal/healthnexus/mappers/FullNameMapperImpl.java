package sh.surge.kunal.healthnexus.mappers;

import sh.surge.kunal.healthnexus.dtos.FullNameResponse;
import sh.surge.kunal.healthnexus.models.FullName;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-30T00:28:02+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Homebrew)"
)
@Component
public class FullNameMapperImpl implements FullNameMapper {

    @Override
    public FullNameResponse toDTOs(FullName fullName) {
        if ( fullName == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;

        firstName = fullName.getFirstName();
        lastName = fullName.getLastName();

        FullNameResponse fullNameResponse = new FullNameResponse( firstName, lastName );

        return fullNameResponse;
    }
}
