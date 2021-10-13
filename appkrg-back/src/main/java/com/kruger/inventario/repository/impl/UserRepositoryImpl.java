package com.kruger.inventario.repository.impl;

import com.kruger.inventario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl {

    @Autowired
    UserRepository userRepository;

}
