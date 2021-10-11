package com.kruger.inventario.repository;

import com.kruger.inventario.model.PermissionProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface PermissionProfileRepository extends JpaRepository<PermissionProfile, Long> {

    @Query(value = "SELECT UPPER(p.description) AS \"authority\" FROM sec_user u " +
            "INNER JOIN sec_permission_profile pp ON pp.id_profile = u.id_profile " +
            "INNER JOIN sec_permission p ON pp.id_permission = p.id_permission WHERE u.username LIKE ?1 " +
            "UNION " +
            "SELECT UPPER(m.description) AS \"authority\" FROM sec_profile m " +
            "INNER JOIN sec_user u ON u.id_profile = m.id_profile " +
            "WHERE u.username LIKE ?1", nativeQuery = true)
    List<GrantedAuthority> getAuthorities(String username);
}
