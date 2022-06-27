package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentForm {
    private long userId;
    private long doctorId;
    private int time;
    private String day;
    private String description;
    private String symptomps;
}
