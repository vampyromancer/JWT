package kz.zaletov.spring.springsecurity.controllers;

import jakarta.validation.Valid;
import kz.zaletov.spring.springsecurity.dto.AuthenticationDTO;
import kz.zaletov.spring.springsecurity.dto.PersonDTO;
import kz.zaletov.spring.springsecurity.models.Person;
import kz.zaletov.spring.springsecurity.security.JWTUtil;
import kz.zaletov.spring.springsecurity.services.PersonDetailsService;
import kz.zaletov.spring.springsecurity.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RegistrationController {

    private final PersonDetailsService personDetailsService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegistrationController(PersonDetailsService personDetailsService, UserValidator userValidator, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.personDetailsService = personDetailsService;
        this.userValidator = userValidator;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("personForm", new Person());
        return "registration";
    }

    @PostMapping("/registration")
    public Map<String, String> addUser(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
        userValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return Map.of("errorMessage", "Ошибка!"); //Jackson это сам представит в JSON, но лучше через
                                             //@ExceptionHandler, как мы делали ранее
        }
        personDetailsService.register(person);
        String token = jwtUtil.generateToken(person.getUsername()); //формируем токен
        return Map.of("jwt-token", token); //возвращаем токен клиенту
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        }catch (BadCredentialsException e){
            return Map.of("message", "Incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }
    public Person convertToPerson(PersonDTO personDTO){
        Person person = new Person();
        person.setUsername(personDTO.getUsername());
        person.setPassword(personDTO.getPassword());
        person.setAge(personDTO.getAge());
        person.setPasswordConfirm(personDTO.getPasswordConfirm());
        return person;
    }
}