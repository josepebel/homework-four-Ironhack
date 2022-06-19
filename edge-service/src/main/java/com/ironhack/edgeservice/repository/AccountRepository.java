package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findById(int id);

    @Query(value = "SELECT DISTINCT AVG(middle_values) FROM (SELECT t1.employee_count AS 'middle_values' " +
            "FROM (SELECT @row\\:=@row+1 as `row`, x.employee_count FROM account AS x, (SELECT @row\\:=0) AS r WHERE 1 ORDER BY x.employee_count) AS t1, " +
            "(SELECT COUNT(*) as 'count' FROM account x WHERE 1) AS t2 WHERE t1.row >= t2.count/2 and t1.row <= ((t2.count/2) +1)) AS t3;", nativeQuery = true)
    BigDecimal medianEmployeeCount();

    @Query("SELECT DISTINCT avg(employeeCount) FROM Account a")
    BigDecimal meanEmployeeCount();

    @Query("SELECT DISTINCT id, employeeCount FROM Account WHERE employeeCount = (SELECT max(employeeCount) FROM Account)")
    List<Object[]> maxEmployeeCount();

    @Query("SELECT DISTINCT id, employeeCount FROM Account WHERE employeeCount =(SELECT min(employeeCount) FROM Account)")
    List<Object[]> minEmployeeCount();

    @Query("SELECT DISTINCT product, count(*) FROM Opportunity o GROUP BY product")
    List<Object[]> countOppsByProduct();

    @Query("SELECT DISTINCT product, count(*) FROM Opportunity o WHERE o.status = :status GROUP BY product")
    List<Object[]> countOppsByStatusAndProduct(@Param("status") Status status);

    @Query("SELECT DISTINCT a.country, count(o) FROM Account a LEFT JOIN a.opportunityList o GROUP BY a.country")
    List<Object[]> countOppsByCountry();

    @Query("SELECT DISTINCT a.country, count(o) FROM Account a LEFT JOIN a.opportunityList o WHERE o.status = :status GROUP BY a.country")
    List<Object[]> countOppsByStatusAndCountry(@Param("status") Status status);

    @Query("SELECT DISTINCT a.city, count(o) FROM Account a LEFT JOIN a.opportunityList o GROUP BY a.city")
    List<Object[]> countOppsByCity();

    @Query("SELECT DISTINCT a.city, count(o) FROM Account a LEFT JOIN a.opportunityList o WHERE o.status = :status GROUP BY a.city")
    List<Object[]> countOppsByStatusAndCity(@Param("status") Status status);

    @Query("SELECT DISTINCT a.industry, count(o) FROM Account a LEFT JOIN a.opportunityList o GROUP BY a.industry")
    List<Object[]> countOppsByIndustry();

    @Query("SELECT DISTINCT a.industry, count(o) FROM Account a LEFT JOIN a.opportunityList o WHERE o.status = :status GROUP BY a.industry")
    List<Object[]> countOppsByStatusAndIndustry(@Param("status") Status status);

    List<Account> findByUserCreation(String name);
}