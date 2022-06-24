package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
    private long statusId;
    private String name;
    private String description;

}
