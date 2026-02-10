package sh.surge.kunal.healthnexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication // SpringBoot Annotation
@EnableDiscoveryClient // Eureka Annotation
public class HealthNexusAPI {

	public static void main(String[] args) {
		SpringApplication.run(HealthNexusAPI.class, args);
	}

}
