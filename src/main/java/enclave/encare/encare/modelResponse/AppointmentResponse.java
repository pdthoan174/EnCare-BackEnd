package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private long appointmentId;
    private String symptoms;
    private String description;

    private String time;
    private String createDate;
    private DoctorResponse doctorResponse;
    private UserResponse userResponse;

    private StatusResponse statusResponse;
}
