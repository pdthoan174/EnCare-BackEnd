package enclave.encare.encare.controller;

import enclave.encare.encare.form.LoginForm;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getPhone(),loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Account account = customUserDetail.getAccount();
        String token = jwtTokenProvider.generateToken(customUserDetail);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","type: "+account.getRole(), token)
        );
    }


}
