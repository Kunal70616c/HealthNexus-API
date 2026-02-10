package sh.surge.kunal.healthnexus.mappers;

import sh.surge.kunal.healthnexus.dtos.FullNameResponse;
import sh.surge.kunal.healthnexus.dtos.PatientResponse;
import sh.surge.kunal.healthnexus.models.Gender;
import sh.surge.kunal.healthnexus.models.Patient;
import java.time.LocalDate;
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
public class PatientMapperImpl implements PatientMapper {

    @Autowired
    private FullNameMapper fullNameMapper;

    @Override
    public PatientResponse toDTOs(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        FullNameResponse fullNameResponse = null;
        LocalDate dob = null;
        long contactNo = 0L;
        String adhaarCardNo = null;
        String email = null;
        Gender gender = null;
        String ailment = null;
        String occupation = null;

        fullNameResponse = fullNameMapper.toDTOs( patient.getFullName() );
        dob = patient.getDateOfBirth();
        contactNo = patient.getContactNumber();
        adhaarCardNo = patient.getAdhaarCardNo();
        email = patient.getEmail();
        gender = patient.getGender();
        ailment = patient.getAilment();
        occupation = patient.getOccupation();

        PatientResponse patientResponse = new PatientResponse( adhaarCardNo, fullNameResponse, email, gender, dob, contactNo, ailment, occupation );

        return patientResponse;
    }

    @Override
    public List<PatientResponse> toDTOs(List<Patient> patients) {
        if ( patients == null ) {
            return null;
        }

        List<PatientResponse> list = new ArrayList<PatientResponse>( patients.size() );
        for ( Patient patient : patients ) {
            list.add( toDTOs( patient ) );
        }

        return list;
    }
}
