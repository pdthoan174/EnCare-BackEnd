package enclave.encare.encare.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {
    @NotNull
    private long channelId;
    @NotNull
    private long accountId;
    @NotBlank
    private String message;
}
