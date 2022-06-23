package enclave.encare.encare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @RequestMapping("/abc")
    public String index(){
        return "index doctor";
    }
}
