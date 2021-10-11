package com.kruger.inventario.repository.impl;

import com.kruger.inventario.model.User;
import com.kruger.inventario.repository.PermissionProfileRepository;
import com.kruger.inventario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionProfileRepository permissionProfileRepository;

    public UsuarioDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usr = userRepository.getUserByUsername(username);

        if(usr != null) {
            List<GrantedAuthority> auth = permissionProfileRepository.getAuthorities(usr.getUsername());
            boolean state;

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : auth) {
                SimpleGrantedAuthority s = new SimpleGrantedAuthority(grantedAuthority.getAuthority());
                authorities.add(s);
            }

            state = usr.getState().getIdState() == 1;

            org.springframework.security.core.userdetails.User suser = new org.springframework.security.core.userdetails.User(usr.getUsername(),usr.getPassword(),state,true,true,true,authorities);
            return suser;// new org.springframework.security.core.userdetails.User(usr.getUsername(),usr.getPassword(),state,true,true,true,authorities);
        }else{

            throw new UsernameNotFoundException(
                    String.format("Username not found for domain, username=%s, domain=%s",
                            username));
        }
    }
}
