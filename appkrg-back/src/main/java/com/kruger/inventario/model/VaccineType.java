package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="vaccineType")
@Table(name="lst_vaccinetype")
@Getter
@Setter
public class VaccineType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_vaccine_type",nullable = false, unique = true)
    private Long idVaccineType;

    @Column
    private String description;
}
