package enclave.encare.encare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    private String phone;
    private String password;
    private String role;
    private String name;
    private String avatar;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @OneToOne(mappedBy = "account")
    private User user;
    @OneToOne(mappedBy = "account")
    private Doctor doctor;

    @OneToMany(mappedBy = "account")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Message> messageList;
}
