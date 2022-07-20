package enclave.encare.encare.controller;

import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.modelResponse.HospitalResponse;
import enclave.encare.encare.modelResponse.UserResponse;
import enclave.encare.encare.service.CategoryService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.HospitalService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ResponseObject> informationDoctor(@PathVariable("doctorId") long doctorId){
        DoctorResponse doctorResponse = doctorService.findById(doctorId);
        if (doctorResponse==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"This doctor does not exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Information of doctor", doctorResponse)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ResponseObject> informationPatient(@PathVariable("patientId") long patientId){
        UserResponse userResponse = userService.findById(patientId);
        if (userResponse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"This patient does not exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Information of patient", userResponse)
        );
    }

    @GetMapping("/doctor")
    public ResponseEntity<ResponseObject> findDoctorLikeName(
            @RequestParam(required = true, name = "name") String name,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List doctor by name", doctorService.findLikeName(name, page))
        );
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<ResponseObject> informationHospital(@PathVariable("hospitalId") long hospitalId){
        HospitalResponse hospitalResponse = hospitalService.findById(hospitalId);
        if (hospitalResponse==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "This hospital does not exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Information of hospital", hospitalResponse)
        );
    }
}
