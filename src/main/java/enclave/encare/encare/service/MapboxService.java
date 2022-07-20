package enclave.encare.encare.service;

import enclave.encare.encare.config.NumberConfig;
import enclave.encare.encare.form.mapbox.Distance;
import enclave.encare.encare.form.mapbox.Location;
import enclave.encare.encare.form.mapbox.MapboxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapboxService {
    @Value("${cloud.mapbox.token}")
    private String token;

    public Distance getDistance(Location start, Location end){
        Distance distance = new Distance();

        String url = "https://api.mapbox.com/directions/v5/mapbox/driving/" +
                ""+start.getLon()+","+start.getLat()+";" +
                ""+end.getLon()+","+end.getLat()+
                "?access_token="+token;

        RestTemplate restTemplate = new RestTemplate();
        MapboxResponse object = restTemplate.getForObject(url, MapboxResponse.class);
        float time = object.getRoutes().get(0).getDuration()/60f;//minute
        float km = object.getRoutes().get(0).getDistance()/1000f;
        distance.setDuration(NumberConfig.round(time));
        distance.setDistance(NumberConfig.round(km));

        return distance;
    }
}
