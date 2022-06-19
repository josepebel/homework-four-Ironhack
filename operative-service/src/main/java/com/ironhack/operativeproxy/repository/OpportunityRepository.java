package com.ironhack.operativeproxy.repository;

import com.ironhack.operativeproxy.enums.Status;
import com.ironhack.operativeproxy.model.Opportunity;
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

    Optional<Opportunity> findById(int id);

    List<Opportunity> findAll();

    List<Opportunity> findByUserId(int id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Opportunity SET status = :status WHERE id = :id")
    void updateOpportunityStatus(@Param("id") int id, @Param("status") Status status);
}
