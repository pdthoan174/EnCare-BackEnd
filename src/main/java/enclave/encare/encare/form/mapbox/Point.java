package enclave.encare.encare.form.mapbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    private double distance;
    private String name;
    private List<Double> location;
}
