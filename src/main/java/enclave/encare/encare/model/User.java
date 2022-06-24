package enclave.encare.encare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Channel> channelIdList;

    public User(long userId){
        this.userId = userId;
    }
}
