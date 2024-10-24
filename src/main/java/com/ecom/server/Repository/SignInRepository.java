package com.ecom.server.Repository;


import com.ecom.server.Model.SignInModel;
import com.ecom.server.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignInRepository extends JpaRepository<UserModel,Long> {
    UserModel findByUsername(String username);
}
