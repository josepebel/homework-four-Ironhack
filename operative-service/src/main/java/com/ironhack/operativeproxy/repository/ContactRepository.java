package com.ironhack.operativeproxy.repository;

import com.ironhack.operativeproxy.model.person.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
