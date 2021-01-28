package hcl.graphql.services.gqlfederation.tracktransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hcl.graphql.services.*")
public class TransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferApplication.class, args);
	}

}
