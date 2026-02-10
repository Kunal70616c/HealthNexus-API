package sh.surge.kunal.healthnexus.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sh.surge.kunal.healthnexus.dtos.AddressDTO;
import sh.surge.kunal.healthnexus.dtos.AddressResponse;
import sh.surge.kunal.healthnexus.dtos.GenericMessage;
import sh.surge.kunal.healthnexus.mappers.AddressMapper;
import sh.surge.kunal.healthnexus.models.Address;
import sh.surge.kunal.healthnexus.services.AddressService;

import jakarta.websocket.server.PathParam;


@RestController
@RequestMapping("/addresses")
public class AddressController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private AddressMapper addressMapper;
	@PostMapping("/v1.0/{adhaarCardNo}")
	public ResponseEntity<GenericMessage> addAddress(@RequestBody AddressDTO addressDTO,@PathParam("adhaarCardNo") String adhaarCardNo){
		//dto to entity
		Address address=addressMapper.dtotoentity(addressDTO);
		Address savedAddress=addressService.addAddress(adhaarCardNo,address);
		//entity to dto
		AddressResponse addressResponse=addressMapper.entitytodto(savedAddress);
		GenericMessage genericMessage=new GenericMessage(addressResponse);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(genericMessage);
	}
	
	@GetMapping("/v1.0")
	public ResponseEntity<GenericMessage> getAllAddresses(@RequestParam String adhaarCardNo){
		List<Address> addresses=addressService.getAllAddresses(adhaarCardNo);
		List<AddressResponse> addressResponse=addressMapper.entitytodto(addresses);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new GenericMessage(addressResponse));
	}
	

}
