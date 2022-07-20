package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackForm {
    private long accountUserId;
    @NotNull
    @Min(0)
    private long appointmentId;
    @NotNull
    @Min(1)
    @Max(5)
    private float rating;
    @NotBlank
    @NotNull
    private String comment;
}
