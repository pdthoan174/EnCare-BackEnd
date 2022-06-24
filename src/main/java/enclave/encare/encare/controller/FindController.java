package enclave.encare.encare.controller;

import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.service.CategoryService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.HospitalService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
public class FindController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HospitalService hospitalService;

    @GetMapping("/doctorId={doctorId}")
    public ResponseEntity<ResponseObject> informationDoctor(@PathVariable("doctorId") long doctorId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Information of user", doctorService.findById(doctorId))
        );
    }

    @GetMapping("/hospitalId={hospitalId}")
    public ResponseEntity<ResponseObject> informationHospital(@PathVariable("hospitalId") long hospitalId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Information of hospital", hospitalService.findById(hospitalId))
        );
    }
}
