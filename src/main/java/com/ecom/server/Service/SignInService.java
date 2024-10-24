package com.ecom.server.Service;

import com.ecom.server.Model.RegistrationModel;
import com.ecom.server.Repository.SignInRepository;
import com.ecom.server.Repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignInService {

    @Autowired
    private SignInRepository signrepo;

    @Autowired
    private UserRegistrationRepository registerRepo;

    public Optional<RegistrationModel> doSignIn(String username){
//        List<UserModel> data=signrepo.findAll();

        System.out.println("data is here:" + username);
       var data= Optional.ofNullable(registerRepo.findByusername(username));
        System.out.println("in service" + data);
        return data;
    }

}
