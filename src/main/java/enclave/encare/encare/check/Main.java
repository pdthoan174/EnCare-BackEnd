package enclave.encare.encare.check;

import enclave.encare.encare.config.RegexConfig;
import enclave.encare.encare.form.mapbox.MapboxResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String pass = "1111111111";
        System.out.println(BCrypt.checkpw(pass, "$2a$10$HErZ2EF8v7.QQpx/5qVDjunqgjZY3LMXi4Orda8sjlSPwnGXFPeAu"));
    }
}
