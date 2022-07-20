package enclave.encare.encare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long channelId;

    @ManyToOne
    @JoinColumn(name = "accountDoctorId")
    private Account doctor;

    @ManyToOne
    @JoinColumn(name = "accountUserId")
    private Account user;

    @OneToMany(mappedBy = "channel")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Message> messageList;

    public Channel(long channelId){
        this.channelId = channelId;
    }
}
