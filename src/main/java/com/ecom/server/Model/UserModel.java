package com.ecom.server.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserModel {
    @Id
    private Long id;

    private String username;
    private String password;
}
