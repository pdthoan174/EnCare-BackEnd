package enclave.encare.encare.form.mapbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leg {
    private List<Object> via_waypoints;
    private List<Admin> admins;
    private double weight;
    private double duration;
    private List<Object> steps;
    private double distance;
    private String summary;
}
