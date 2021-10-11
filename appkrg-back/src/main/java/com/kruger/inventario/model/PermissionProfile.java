package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="permissionprofile")
@Table(name="sec_permission_profile")
@Getter
@Setter
public class PermissionProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_SEQ")
    @SequenceGenerator(name="PERMISSION_SEQ", sequenceName = "PERMISSION_SEQ",allocationSize=1)
    @Column(name="id_permission_profile")
    private long idPermissionProfile;

    @OneToOne
    @JoinColumn(name="id_profile")
    private Profile profile;

    @OneToOne
    @JoinColumn(name="id_permission")
    private Permission permission;
}
