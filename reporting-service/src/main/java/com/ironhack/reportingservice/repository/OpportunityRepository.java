package com.ironhack.reportingservice.repository;

import com.ironhack.reportingservice.enums.Status;
import com.ironhack.reportingservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

    @Query(value = "SELECT DISTINCT AVG(middle_values) FROM (SELECT tab1.count_opps AS 'middle_values' " + "FROM (SELECT @row\\:=@row+1 as `row`, x.count_opps FROM (SELECT count(*) AS 'count_opps' FROM opportunity o GROUP BY account_id" + ") AS x, \n" +
            "    (SELECT @row\\:=0) AS r WHERE 1 ORDER BY x.count_opps) AS tab1, (SELECT COUNT(*) as 'count' FROM (SELECT count(*) AS 'count_opps' FROM opportunity o GROUP BY account_id) x WHERE 1) AS tab2 WHERE tab1.row >= tab2.count/2 and tab1.row <= ((tab2.count/2) +1)) AS tab3;", nativeQuery = true)
    BigDecimal medianOpportunitiesByAccount();

    @Query(value = "SELECT DISTINCT avg(t.val) FROM (SELECT count(*) as val FROM opportunity o GROUP BY o.account_id) as t", nativeQuery = true)
    BigDecimal meanOpportunitiesByAccount();

    @Query(value = "SELECT DISTINCT max(t.val) FROM (SELECT count(*) as val FROM opportunity o GROUP BY o.account_id) as t", nativeQuery = true)
    BigDecimal maxOpportunitiesByAccount();

    @Query(value = "SELECT DISTINCT min(t.val) FROM (SELECT count(*) as val FROM opportunity o GROUP BY o.account_id) as t", nativeQuery = true)
    BigDecimal minOpportunitiesByAccount();

    Optional<Opportunity> findById(int id);

    List<Opportunity> findAll();

    List<Opportunity> findByUserId(int id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Opportunity SET status = :status WHERE id = :id")
    void updateOpportunityStatus(@Param("id") int id, @Param("status") Status status);

    @Query(value = "SELECT DISTINCT AVG(middle_values) FROM (SELECT t1.quantity AS 'middle_values' " +
        "FROM (SELECT @row\\:=@row+1 as `row`, x.quantity FROM opportunity AS x, (SELECT @row\\:=0) AS r WHERE 1 ORDER BY x.quantity) AS t1, " +
        "(SELECT COUNT(*) as 'count' FROM opportunity x WHERE 1) AS t2 WHERE t1.row >= t2.count/2 and t1.row <= ((t2.count/2) +1)) AS t3;", nativeQuery = true)
    BigDecimal medianQuantity();

    @Query("SELECT DISTINCT avg(quantity) FROM Opportunity o")
    BigDecimal meanQuantity();

    @Query("SELECT DISTINCT id, quantity FROM Opportunity WHERE quantity = (SELECT max(quantity) FROM Opportunity)")
    List<Object[]> maxQuantity();

    @Query("SELECT DISTINCT id, quantity FROM Opportunity WHERE quantity = (SELECT min(quantity) FROM Opportunity)")
    List<Object[]> minQuantity();
}
