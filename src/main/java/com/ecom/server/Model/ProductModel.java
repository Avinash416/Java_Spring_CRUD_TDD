package com.ecom.server.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_products")
public class ProductModel {
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
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
