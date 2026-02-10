package sh.surge.kunal.healthnexus.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import sh.surge.kunal.healthnexus.dtos.PatientResponse;
import sh.surge.kunal.healthnexus.mappers.PatientMapper;
import sh.surge.kunal.healthnexus.models.FullName;
import sh.surge.kunal.healthnexus.models.Gender;
import sh.surge.kunal.healthnexus.models.Patient;
import sh.surge.kunal.healthnexus.services.PatientService;
import com.github.javafaker.Faker;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(PatientController.class)
public class PatientControllerTest {
	@MockitoBean
	private PatientService patientService;
	@Autowired
	private MockMvc mockMvc;
	@MockitoBean
	private PatientMapper patientMapper; 
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void testGetAllPatients() throws Exception {
		List<Patient> patients = getAllPatients();
		List<PatientResponse> responses = new ArrayList<>();
	    for (int i = 0; i < patients.size(); i++) 
	    	responses.add(Mockito.mock(PatientResponse.class)); // safe

		Mockito.when(patientService.getAllPatients()).thenReturn(patients);
		Mockito.when(patientMapper.toDTOs(Mockito.anyList())).thenReturn(responses);
		 mockMvc.perform(get("/patients/v1.0")
		            .with(jwt().authorities(() -> "SCOPE_developer")))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.length()").value(10))
		        .andDo(print());	      
		
	}
	@Test
	public void testaddPatient() throws Exception {
		Patient patient = getAllPatients().get(0);
		Mockito.when(patientService.addPatient(patient)).thenReturn(patient);
		 mockMvc.perform(post("/patients/v1.0")
				 .content(objectMapper.writeValueAsString(patient))   // JSON body
		            .contentType(MediaType.APPLICATION_JSON)
		            .with(jwt().authorities(() -> "SCOPE_developer")))
		        .andExpect(status().isCreated())
		        .andDo(print())
		        .andExpect(jsonPath("$.length()").value(Matchers.greaterThan(0)));
		      
		
	}
	
	
	
	private List<Patient> getAllPatients() {
		Faker faker = new Faker();
		// TODO Auto-generated method stub
		List<Patient> patients = new ArrayList<>();
		for(int i=0;i<10;i++) {
			Patient patient = new Patient();
			patient.setAdhaarCardNo(faker.idNumber().valid());
			FullName name = new FullName();
			name.setFirstName(faker.name().firstName());
			name.setLastName(faker.name().lastName());
			patient.setFullName(name);
			patient.setContactNumber(faker.number().numberBetween(1000000000, 9999999999L));
			patient.setEmail(faker.internet().emailAddress());
			patient.setGender(getRandomGender());
			patient.setAilment(faker.medical().diseaseName());
			patient.setOccupation(faker.company().profession());
			patients.add(patient);
			
		}
		
		return patients;
	}
	
	private  Gender getRandomGender() {
		Gender[] genders=Gender.values();
		return genders[new Random().nextInt(genders.length)];
	}

}
