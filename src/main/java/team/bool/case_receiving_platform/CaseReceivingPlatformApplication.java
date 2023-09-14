package team.bool.case_receiving_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class CaseReceivingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseReceivingPlatformApplication.class, args);
	}

}
