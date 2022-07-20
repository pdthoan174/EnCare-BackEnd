package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordForm {
    private long accountId;
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String oldPassword;
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
