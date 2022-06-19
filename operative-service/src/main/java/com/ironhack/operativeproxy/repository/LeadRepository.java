package com.ironhack.operativeproxy.repository;

import com.ironhack.operativeproxy.enums.UserType;
import com.ironhack.operativeproxy.model.person.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer> {
    Optional<Lead> findById(int id);

    List<Lead> findByUserUserType(UserType userType);

    @Query("SELECT l FROM Lead AS l ORDER BY l.id")
    List<Lead> findAll();

    List<Lead> findByUserId(int id);
}
