package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponse {
    private long hospitalId;
    private String description;
    private long latMap;
    private long longMap;
    private long rating;
    private long countRating;
    private String address;
    private String name;
}
