package com.kruger.inventario;

import com.google.gson.Gson;
import com.kruger.inventario.model.Employee;
import com.kruger.inventario.repository.impl.EmployeeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
public class TestEmployeeFilters {

    private final EmployeeRepositoryImpl employeeRepository;

    @Test
    public void doFilters() throws ParseException {

        Page<Employee> emp = employeeRepository.searchEmployeesByFilters("1","1","05/06/2021","06/08/2021",1,5);
        String json = new Gson().toJson(emp);
        System.out.println(json);
    }
}
