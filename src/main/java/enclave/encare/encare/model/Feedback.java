package enclave.encare.encare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedbackId;
    private String comment;
    private long rating;

    @OneToOne
    @JoinColumn(name = "appointmentId")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Feedback(long feedbackId){
        this.feedbackId = feedbackId;
    }
}
