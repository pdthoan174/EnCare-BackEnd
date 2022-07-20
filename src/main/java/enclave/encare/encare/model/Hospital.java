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
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hospitalId;
    private String description;
    private double latMap;
    private double longMap;
    private long rating;
    private long countRating;
    private String address;
    private String name;

    @OneToMany(mappedBy = "hospital")
    @Cascade(value = org.hibernate.annotations.CascadeType.REMOVE)
    private List<Doctor> doctorList;

    public Hospital(long hospitalId){
        this.hospitalId = hospitalId;
    }

    public Hospital(String description, long latMap, long longMap, long rating, long countRating, String address, String name) {
        this.description = description;
        this.latMap = latMap;
        this.longMap = longMap;
        this.rating = rating;
        this.countRating = countRating;
        this.address = address;
        this.name = name;
    }
}
