package kz.zaletov.spring.springsecurity.repositories;

import kz.zaletov.spring.springsecurity.models.Person;
import kz.zaletov.spring.springsecurity.security.PersonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
