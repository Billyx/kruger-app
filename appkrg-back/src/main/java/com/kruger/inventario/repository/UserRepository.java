package com.kruger.inventario.repository;

import com.kruger.inventario.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value="from user u where u.username like :username")
    User getUserByUsername(String username);

}
