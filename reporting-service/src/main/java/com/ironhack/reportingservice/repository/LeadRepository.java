package com.ironhack.reportingservice.repository;

import com.ironhack.reportingservice.enums.UserType;
import com.ironhack.reportingservice.model.person.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer> {
    Optional<Lead> findById(int id);

    List<Lead> findByUserUserType(UserType userType);

    @Query(value = "SELECT u.name, COUNT(u.name) from leader l join user u on l.user_id = u.id GROUP BY u.name", nativeQuery = true)
    List<Object[]> getCountOfLeadsBySalesRep();

    @Query("SELECT l FROM Lead AS l ORDER BY l.id")
    List<Lead> findAll();

    List<Lead> findByUserId(int id);
}
