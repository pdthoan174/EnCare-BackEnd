package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private long messageId;
    private String message;
    private AccountResponse accountResponse;
    private ChannelResponse channelResponse;
}
