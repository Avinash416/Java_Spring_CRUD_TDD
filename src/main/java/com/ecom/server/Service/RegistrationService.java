package com.ecom.server.Service;

import com.ecom.server.Model.RegistrationModel;
import com.ecom.server.Repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private UserRegistrationRepository repository;

    public RegistrationModel registerUser(RegistrationModel registerData){
      return repository.save(registerData);
    }
}
