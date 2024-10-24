package com.ecom.server.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    @Id
    public Long id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String image;

//    @Data
//    public static class RatingModel {
//        @Id
//        public double rate;
//        public int count;
//    }
}
