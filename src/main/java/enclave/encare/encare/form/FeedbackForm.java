package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackForm {
    private long accountUserId;
    private long appointmentId;
    private float rating;
    private String comment;
}
