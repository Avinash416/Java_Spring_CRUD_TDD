package com.ecom.server.Repository;

import com.ecom.server.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<ProductModel, Long>{ }
