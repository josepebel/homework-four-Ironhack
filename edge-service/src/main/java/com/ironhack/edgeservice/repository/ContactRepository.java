package com.ironhack.edgeservice.repository;

import com.ironhack.edgeservice.model.person.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
