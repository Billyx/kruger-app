package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity(name="user")
@Table(name="sec_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name="USUARIO_SEQ", sequenceName = "USUARIO_SEQ",allocationSize=1)
    @Column(name="id_user")
    private long id;

    @Column(name="username",nullable=false)
    private String username;

    @Column(name="password")
    private String password;

    @OneToOne
    @JoinColumn(name="id_profile")
    private Profile profile;

    @OneToOne
    @JoinColumn(name="id_employee")
    private Employee employee;

    @OneToOne
    @JoinColumn(name="id_state")
    private State state;

}
