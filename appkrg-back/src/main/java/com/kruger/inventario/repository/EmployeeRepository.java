package com.kruger.inventario.repository;

import com.kruger.inventario.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

    @Query(value="select emp.name, emp.lastname from mas_employee emp " +
                "where is_vaccinated LIKE %:isVaccinated% " +
                "and id_vac_type LIKE %:vaccineType% " +
                "and first_vac_date between :vacdateini and :vacdatefin " +
                "or second_vac_date between :vacdateini and :vacdatefin", nativeQuery = true)
    Page<Employee> getEmployeesByFilters(String isVaccinated,
                                          String vaccineType,
                                          String vacdateini,
                                          String vacdatefin,
                                          Pageable limit);

    List<Employee> findEmployeesByFilters(String arg1, String arg2, String arg3, String arg4, int arg5, int arg6) throws ParseException;
}
