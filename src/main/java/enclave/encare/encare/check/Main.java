package enclave.encare.encare.check;

import enclave.encare.encare.config.RegexConfig;
import enclave.encare.encare.form.mapbox.MapboxResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String pass = "31/12/1900";
        System.out.println(pass.matches(RegexConfig.day));
    }
}
