package com.ecom.server.Controller;

import com.ecom.server.Model.ProductModel;
import com.ecom.server.Model.RegistrationModel;
import com.ecom.server.Service.ProductsService;
import com.ecom.server.Service.RegistrationService;
import com.ecom.server.Service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SignInController {

    @Autowired
    private SignInService service;

    @Autowired
    private RegistrationService register;

    @Autowired
    private ProductsService external;

    @GetMapping("/signing/{username}")
    public ResponseEntity<?> handleSign(@PathVariable String username){
        Optional<RegistrationModel> user =service.doSignIn(username);
        if(user.isPresent()){
            return ResponseEntity.ok("user Found");
        }else{
            return ResponseEntity.status(404).body("User not found");
        }
   }

    @PostMapping("/register")
    public ResponseEntity<?> handleRegistration(@RequestBody RegistrationModel registerData){
        RegistrationModel reg = register.registerUser(registerData);
        if(reg != null){
            return ResponseEntity.status(200).body(reg);
        }else{
            return ResponseEntity.status(401).body("Bad request");
        }
    }

}
