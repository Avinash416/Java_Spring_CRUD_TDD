package com.ecom.server.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class RatingModel {
    @Id
    public double rate;
    public int count;
}
