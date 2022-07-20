package enclave.encare.encare.until;

import com.twilio.Twilio;
import enclave.encare.encare.config.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInit {
    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioInit(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
        Twilio.init(
                twilioConfig.getAccountSid(),
                twilioConfig.getAuthToken()
        );
    }
}
