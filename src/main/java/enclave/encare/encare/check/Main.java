package enclave.encare.encare.check;

import enclave.encare.encare.config.RegexConfig;
import enclave.encare.encare.form.mapbox.MapboxResponse;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String regex = RegexConfig.name;
        String test = "acông tằng tôn nữ ";
        System.out.println(test.matches(regex));
    }
}
