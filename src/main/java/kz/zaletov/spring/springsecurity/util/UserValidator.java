package kz.zaletov.spring.springsecurity.util;

import kz.zaletov.spring.springsecurity.models.Person;
import kz.zaletov.spring.springsecurity.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public UserValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDetailsService.findUserByUsername(person.getUsername())!=null)
            errors.rejectValue("username", "", "Такой пользователь уже существует");
        if(!person.getPasswordConfirm().equals(person.getPassword()))
            errors.rejectValue("password", "", "Пароли не совпадают");
    }
}
