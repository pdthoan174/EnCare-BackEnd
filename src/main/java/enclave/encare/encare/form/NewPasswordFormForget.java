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
public class NewPasswordFormForget {
    @NotNull
    @NotBlank
    @Size(min = 6, max = 6)
    private String otp;
    @NotNull
    @NotBlank
    @Size(min = 9, max = 10)
    private String phone;
    @NotBlank
    @NotNull
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
