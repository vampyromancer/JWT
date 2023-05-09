package kz.zaletov.spring.springsecurity.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kz.zaletov.spring.springsecurity.models.Role;
import kz.zaletov.spring.springsecurity.models.User;
import kz.zaletov.spring.springsecurity.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em; //с помощью него можно делать sql запросы
    //можно также через аннотацию @Query в репозитории к сигнатуре
    //@Query(value = "SELECT nextval(pg_get_serial_sequence('t_user', 'id'))", nativeQuery = true)
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    public void register(User user) {
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long userId) {
            userRepository.deleteById(userId);
    }
    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
