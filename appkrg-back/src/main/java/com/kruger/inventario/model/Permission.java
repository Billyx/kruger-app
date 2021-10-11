package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="permission")
@Table(name="sec_permission")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_SEQ")
    @SequenceGenerator(name="PERMISSION_SEQ", sequenceName = "PERMISSION_SEQ",allocationSize=1)
    @Column(name="id_permission")
    private long idPermission;

    @Column
    private String permission;

    @Column
    private String description;

    @Column(name="id_state")
    private long idState;


}
