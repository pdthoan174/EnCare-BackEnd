package enclave.encare.encare.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private long accountId;
    private String phone;
    private String password;
    private String role;
    private String name;
    private String avatar;
    private String description;
    private String birthday;
    private String createDate;
    private String updateDate;
}
