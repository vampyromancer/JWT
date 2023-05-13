package kz.zaletov.spring.springsecurity.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kz.zaletov.spring.springsecurity.models.Person;
import kz.zaletov.spring.springsecurity.models.Role;
import kz.zaletov.spring.springsecurity.repositories.PeopleRepository;
import kz.zaletov.spring.springsecurity.security.PersonDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em; //с помощью него можно делать sql запросы
    //можно также через аннотацию @Query в репозитории к сигнатуре
    //@Query(value = "SELECT nextval(pg_get_serial_sequence('t_user', 'id'))", nativeQuery = true)
    private final PeopleRepository peopleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersonDetailsService(PeopleRepository peopleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.peopleRepository = peopleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new PersonDetails(person.get());
    }
    public Person findUserByUsername(String username){
        return peopleRepository.findByUsername(username).orElse(null);
    }
    public Person findUserById(Long userId) {
        Optional<Person> userFromDb = peopleRepository.findById(userId);
        return userFromDb.orElse(new Person());
    }
    public List<Person> allUsers() {
        return peopleRepository.findAll();
    }
    @Transactional
    public void register(Person person) {
        person.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
    public void delete(Long userId) {
            peopleRepository.deleteById(userId);
    }
    public List<Person> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM Person u WHERE u.id > :paramId", Person.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
