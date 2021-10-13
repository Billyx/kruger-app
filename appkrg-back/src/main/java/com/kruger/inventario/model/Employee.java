package com.kruger.inventario.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="employee")
@Table(name="mas_employee")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    @Column(name = "id_employee")
    private long id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private String mlastname;

    @Column
    private String email;

    @Column
    private String icard;

    @Column(name="born_date")
    private String borndate;

    @Column
    private String address;

    @Column(name="mobilephone")
    private String mobilephone;

    @Column(name="is_vaccinated")
    private String isVaccinated;

    @Column(name="first_vac_date")
    private String firstVacDate;

    @Column(name="second_vac_date")
    private String secondVacDate;

    @OneToOne
    @JoinColumn(name="id_vac_type")
    private VaccineType vaccineType;


}
