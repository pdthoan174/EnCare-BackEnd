package enclave.encare.encare.check;

import enclave.encare.encare.form.mapbox.MapboxResponse;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        String token = "pk.eyJ1Ijoic2l4YmxhY2siLCJhIjoiY2w0dzNpYnVyMXFhYjNlazI5OGF6MGJzcyJ9.DMrrU3tLH2gfvfY1wK9G4w";

        RestTemplate restTemplate = new RestTemplate();
        MapboxResponse object = restTemplate.getForObject("https://api.mapbox.com/directions/v5/mapbox/driving/108.24013182677783,15.975729316697397;108.22913866009225,16.019262542089308?access_token=pk.eyJ1Ijoic2l4YmxhY2siLCJhIjoiY2w0dzNpYnVyMXFhYjNlazI5OGF6MGJzcyJ9.DMrrU3tLH2gfvfY1wK9G4w", MapboxResponse.class);
//        Object object = restTemplate.getForObject(url, Object.class);
        System.out.println("time: "+object.getRoutes().get(0).getDuration()/60);
        System.out.println("distance: "+object.getRoutes().get(0).getDistance());

    }
}
