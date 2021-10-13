package com.kruger.inventario.rest;

import com.google.gson.Gson;
import com.kruger.inventario.model.Employee;
import com.kruger.inventario.repository.impl.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class RestEmployee {

    private final EmployeeRepositoryImpl repository;

    @Autowired
    public RestEmployee(EmployeeRepositoryImpl repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/getEmployeesByFilters", method = RequestMethod.GET, produces = {"application/json"})
    public String getUsersByFilters(@RequestParam String evac, @RequestParam String tvac,
                                    @RequestParam String dateini, @RequestParam String datefin,
                                    @RequestParam int page, @RequestParam int qrecords) throws ParseException {

        //Page<Employee> list = repository.searchEmployeesByFilters(evac,tvac,dateini,datefin,page,qrecords);
        List<Employee> list = repository.findEmployeesByFilters(evac,tvac,dateini,datefin,page,qrecords);
        String json = new Gson().toJson(list);
        return json;
    }

}
