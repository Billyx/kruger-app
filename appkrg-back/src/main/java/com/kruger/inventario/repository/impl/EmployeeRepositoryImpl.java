package com.kruger.inventario.repository.impl;

import com.kruger.inventario.model.Employee;
import com.kruger.inventario.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeRepositoryImpl {

    private final EmployeeRepository employeeRepository;

    /**
     *
     * @param arg1 evac: state of vaccine
     * @param arg2 tvac: type of vaccine
     * @param arg3 inivacdate: init date of vaccine
     * @param arg4 finvacdate: final date of vaccine
     * @param arg5 Number of page
     * @param arg6 Number of records to show
     * @return
     */

    public Page<Employee> searchEmployeesByFilters(String arg1, String arg2,
                                                   String arg3, String arg4,
                                                   int arg5,int arg6){
        Pageable limit = PageRequest.of(arg5,arg6, Sort.by("id").ascending());
        Page<Employee> user = employeeRepository.getEmployeesByFilters(arg1,arg2,arg3,arg4,limit);
        return user;
    }

    public List<Employee> findEmployeesByFilters(String arg1, String arg2,
                                                   String arg3, String arg4,
                                                   int arg5,int arg6) throws ParseException {
        //Pageable limit = PageRequest.of(arg5,arg6, Sort.by("id").ascending());
        List<Employee> user = employeeRepository.findEmployeesByFilters(arg1,arg2,arg3,arg4,arg5,arg6);
        return user;
    }

}
