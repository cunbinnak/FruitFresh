package com.fruitfresh.user;

import com.fruitfresh.Products.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername (String username);

    User findByTokenforgotpass(String token);

    User findByUsernameAndUserstatus (String username, boolean status);

    Page<User> findAll(Pageable pageable);
}
