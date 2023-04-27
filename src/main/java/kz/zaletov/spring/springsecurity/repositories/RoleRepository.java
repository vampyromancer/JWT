package kz.zaletov.spring.springsecurity.repositories;

import kz.zaletov.spring.springsecurity.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
