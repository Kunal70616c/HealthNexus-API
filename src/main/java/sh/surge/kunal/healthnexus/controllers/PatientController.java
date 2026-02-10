package sh.surge.kunal.healthnexus.controllers;

import java.util.List;

import sh.surge.kunal.healthnexus.dtos.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sh.surge.kunal.healthnexus.dtos.GenericMessage;
import sh.surge.kunal.healthnexus.dtos.PatientDTO;
import sh.surge.kunal.healthnexus.mappers.PatientMapper;
import sh.surge.kunal.healthnexus.models.FullName;
import sh.surge.kunal.healthnexus.models.Patient;
import sh.surge.kunal.healthnexus.services.PatientService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
	private PatientService patientService; //Service for patient
    @Autowired
    private PatientMapper patientMapper; //Mapping Patient to PatientResponse
    @PreAuthorize("hasAnyAuthority('SCOPE_developer')") //Only developer can add patient
    @PostMapping("/v1.0")
    public ResponseEntity<GenericMessage<String>> addPatient(@Valid @RequestBody PatientDTO patientDTO) {
		//mapping DTO to entity
        //Building Full Name
    	FullName fullName = FullName.builder()
				.firstName(patientDTO.getFullName().getFirstName())
				.lastName(patientDTO.getFullName().getLastName())
				.build();
        //Building Patient
    	Patient patient = Patient.builder()
    			.fullName(fullName)
    			.dateOfBirth(patientDTO.getDateOfBirth())
    			.email(patientDTO.getEmail())
    			.contactNumber(patientDTO.getContactNumber())
    			.gender(patientDTO.getGender())
    			.ailment(patientDTO.getAilment())
    			.occupation(patientDTO.getOccupation())
    			.build();
        //Saving patient
    	Patient savedPatient = patientService.addPatient(patient);
    	PatientResponse patientResponse = patientMapper.toDTOs(savedPatient);
    	return ResponseEntity.status(HttpStatus.CREATED)
				.body(new GenericMessage(patientResponse));
	}

    @PreAuthorize("hasAnyAuthority('SCOPE_tester','SCOPE_developer')")
    //Only tester and developer can get all patients
    @GetMapping("/v1.0")
    public List<PatientResponse> getAllPatients() {
    	List<Patient> patients=	patientService.getAllPatients();
    	List<PatientResponse> patientResponses = patientMapper.toDTOs(patients);
    	return patientResponses;
    	
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_tester','SCOPE_developer')")
    //Only tester and developer can get patient by adhaar card no
    @GetMapping("/v1.0/{adhaarCardNo}")
    // @PathParam("adhaarCardNo") is used to get the adhaar card no from the request
	public ResponseEntity<GenericMessage<String>> getPatientByAdhaarCardNo(@PathParam("adhaarCardNo") String adhaarCardNo) {
		//Getting patient by adhaar card no and mapping to response
        //Building Patient
		Patient patient = patientService.getPatientByAdhaarCardNo(adhaarCardNo);
		PatientResponse patientResponse = patientMapper.toDTOs(patient);
    	return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new GenericMessage(patientResponse));
	}
    @PreAuthorize("hasAnyAuthority('SCOPE_developer')")
    //Only developer can update patient by adhaar card no
    @PatchMapping("/v1.0")
    public ResponseEntity<GenericMessage<String>> updatePatientByEmailAndPhoneNumber(
    		@RequestParam String adhaarCardNo,
    		@RequestParam long contactNo,@RequestParam String email) {
    		Patient updatedPatient = patientService.updatePatient(adhaarCardNo, contactNo, email);
    	    PatientResponse patientResponse = patientMapper.toDTOs(updatedPatient);
    		return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new GenericMessage(patientResponse));
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_developer')")
    @DeleteMapping("/v1.0")
    public ResponseEntity<GenericMessage<String>> deletePatientByAdhaarCardNo(
			@RequestParam String adhaarCardNo) {
			boolean isDeleted = patientService.deletePatient(adhaarCardNo);
			if(isDeleted) {
			    return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new GenericMessage<>("Patient deleted successfully with Adhaar Card No: "
				+ adhaarCardNo));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new GenericMessage<>("Patient not found with Adhaar Card No: "
				+ adhaarCardNo));
			}
	}
    
}
