package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationForm {
    private long accountId;
    private String password;
    private String name;
    private String description;
    private String birthDay;
}
