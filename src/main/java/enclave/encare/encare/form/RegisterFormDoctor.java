package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormDoctor {
    @NotBlank
    @NotNull
    @Size(min = 9, max = 10)
    private String phone;
    @NotBlank
    @NotNull
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 40)
    private String name;
    @NotBlank
    @NotNull
    private String description;
    private String birthDay;
    @Min(0)
    private long hospitalId;
    @Min(0)
    private long categoryId;
}
