package com.kruger.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<com.kruger.inventario.model.Permission, Long> {
    PermissionRepository findPermissionByIdPermission(long idPermission);
}
