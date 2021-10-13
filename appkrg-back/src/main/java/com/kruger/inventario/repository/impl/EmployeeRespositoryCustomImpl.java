package com.kruger.inventario.repository.impl;

import com.kruger.inventario.model.Employee;
import com.kruger.inventario.repository.EmployeeRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeRespositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> findEmployeesByFilters(String arg1, String arg2,
                                                 String arg3, String arg4,
                                                 int arg5, int arg6) throws ParseException {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employees = query.from(Employee.class);

        Predicate evacp = null; // Estado de vacunación
        Predicate tvacp = null; // Tipo de vacunación
        Predicate dvac = null; // Fecha inicio de vacunación

        if(arg1 != null){
            evacp = cb.equal(employees.get("evac"),arg1);
        }
        if(arg2 != null){
            tvacp = cb.equal(employees.get("vaccineType"),arg2);
        }
        if(arg3 != null && arg4 != null){
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(arg3);
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(arg4);
            dvac = cb.between(employees.<Date>get("firstVacDate"), date1, date2);
        }

        Predicate etp = cb.or(evacp,tvacp);
        Predicate dp = cb.or(dvac);
        Predicate finalPredicate = cb.or(etp,dp);

        //query.select(employees).where(finalPredicate);
        //Page<Employee> list = entityManager.createQuery(query).getResultList();

        // Pagination
        int page = arg5;
        int qrecords = arg6;

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(Employee.class))).where(finalPredicate);
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<Employee> select = query.select(employees).where(finalPredicate);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(select);
        while (page < count.intValue()) {
            typedQuery.setFirstResult(page - 1);
            typedQuery.setMaxResults(qrecords);
            System.out.println("Current page: " + typedQuery.getResultList());
            //page += qrecords;
        }


        return typedQuery.getResultList();
    }
}
