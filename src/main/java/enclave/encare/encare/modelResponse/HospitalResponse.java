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
    private double latMap;
    private double longMap;
    private long rating;
    private long countRating;
    private String address;
    private String name;
}
