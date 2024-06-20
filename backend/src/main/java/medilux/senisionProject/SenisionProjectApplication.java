package medilux.senisionProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SenisionProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenisionProjectApplication.class, args);
	}

}
