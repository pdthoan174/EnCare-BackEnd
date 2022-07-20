package enclave.encare.encare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;
    private String name;
    @Type(type = "text")
    private String description;

    @OneToMany(mappedBy = "category")
    @Cascade(value = org.hibernate.annotations.CascadeType.REMOVE)
    private List<Doctor> doctorList;

    public Category(long categoryId){
        this.categoryId = categoryId;
    }
    public Category(String name, String description){
        this.name = name;
        this.description = description;
    }
}
