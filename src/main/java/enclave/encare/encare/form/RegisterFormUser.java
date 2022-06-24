package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFormUser {
    private String phone;
    private String password;
    private String name;
    private String description;
    private String birthDay;
}
