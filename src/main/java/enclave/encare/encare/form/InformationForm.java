package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationForm {
    private long accountId;
    @NotBlank
    @NotEmpty
    private String name;
    private String description;
    private String birthDay;
}
