package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorInformationForm extends InformationForm {
    private long doctorId ;
    private long categoryId;
    private long hospitalId ;
    private String avatar;
    private String phone;

}
