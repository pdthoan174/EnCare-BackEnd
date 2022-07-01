package enclave.encare.encare.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentForm {
    private long accountUserId;
    @NotNull(message = "Don't have doctor Id")
    @Min(1)
    private long doctorId;
    @NotNull
    @Min(7)
    @Max(16)
    private int time;
    @NotBlank(message = "You haven't choose a day yet")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String day;
    private String description;
    private String symptomps;
}
