package com.fruitfresh.Products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Products, Integer> {

    Products findByProductid(Integer id);

    Page<Products> findAll(Pageable pageable);
}
