package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelResponse {
    private long channelId;
    private DoctorResponse doctorResponse;
    private UserResponse userResponse;

}
