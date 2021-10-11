package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="profile")
@Table(name="sec_profile")
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_profile",nullable = false, unique = true)
    private Long idProfile;

    @Column(name="description")
    private String description;

    @OneToOne
    @JoinColumn(name="id_state")
    private State state;
}
