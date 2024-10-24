package com.ecom.server.Repository;

import com.ecom.server.Model.RegistrationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRegistrationRepository extends JpaRepository<RegistrationModel,Long> {
    RegistrationModel findByusername(String username);
}
