package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotNull
    @NotBlank
    @Length(min = 9, max = 11)
    private String phone;
    @NotNull
    @NotBlank
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
