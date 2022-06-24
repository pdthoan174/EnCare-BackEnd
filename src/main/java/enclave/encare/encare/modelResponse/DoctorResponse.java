package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    private long doctorId;
    private float rating;
    private long countRating;

    private AccountResponse accountResponse;
    private CategoryResponse categoryResponse;
    private HospitalResponse hospitalResponse;
}
