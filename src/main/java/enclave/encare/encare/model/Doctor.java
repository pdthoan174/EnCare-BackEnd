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
@Table(name = "doctor")
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;
    private float rating;
    private long countRating;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "hospitalId")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "doctor")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "doctor")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Channel> channelIdList;

    public Doctor(long doctorId){
        this.doctorId = doctorId;
    }
}
