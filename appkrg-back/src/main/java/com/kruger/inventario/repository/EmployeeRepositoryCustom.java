package com.kruger.inventario.repository;

import com.kruger.inventario.model.Employee;
import java.text.ParseException;
import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeesByFilters(String arg1, String arg2, String arg3, String arg4, int arg5, int arg6) throws ParseException;
}
