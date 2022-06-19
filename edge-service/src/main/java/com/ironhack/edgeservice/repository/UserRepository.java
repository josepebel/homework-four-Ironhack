package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.enums.Status;
import com.ironhack.edgeservice.enums.UserType;
import com.ironhack.edgeservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT DISTINCT u FROM User AS u WHERE u.userType = :user")
    List<User> getAllUserByUserType(@Param("user") UserType user);

    @Query("SELECT DISTINCT u FROM User AS u LEFT JOIN FETCH u.opportunityList WHERE u.userType = :user ORDER BY u.id")
    List<User> getAllUserWithOpportunityByUserType(@Param("user") UserType user);

    @Query("SELECT DISTINCT u FROM User AS u JOIN FETCH u.opportunityList AS o WHERE u.userType = :user" +
            " AND o.status = :status")
    List<User> getAllUserWithOpportunityByUserTypeAndStatus(@Param("user") UserType user,
                                                            @Param("status") Status status);

    Optional<User> findByName(String username);
}
