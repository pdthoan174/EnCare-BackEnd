package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPForm {
    @NotNull
    @NotBlank
    @Size(min = 6, max = 6)
    private String otp;
    @NotNull
    @NotBlank
    @Size(min = 9, max = 10)
    private String phone;
}
