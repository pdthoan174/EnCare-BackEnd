package enclave.encare.encare;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class EnCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnCareApplication.class, args);
    }

}
