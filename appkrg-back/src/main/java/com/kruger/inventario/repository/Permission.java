package com.kruger.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Permission extends JpaRepository<com.kruger.inventario.model.Permission, Long> {
    Permission findPermissionByIdPermission(long idPermission);
}
