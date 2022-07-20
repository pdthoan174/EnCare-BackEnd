package enclave.encare.encare.form.mapbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private boolean country_crossed;
    private String weight_name;
    private double weight;
    private float duration;
    private float distance;
    private List<Leg> legs;
    private String geometry;
}
