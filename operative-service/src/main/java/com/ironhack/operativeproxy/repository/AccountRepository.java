package com.ironhack.operativeproxy.repository;

import com.ironhack.operativeproxy.enums.Status;
import com.ironhack.operativeproxy.model.Account;
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

    List<Account> findByUserCreation(String name);
}