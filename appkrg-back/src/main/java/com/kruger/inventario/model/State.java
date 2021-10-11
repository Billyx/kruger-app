package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="state")
@Table(name="sec_state")
@Getter
@Setter
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_state")
    private int idState;

    @Column(name="description")
    private String description;
}
