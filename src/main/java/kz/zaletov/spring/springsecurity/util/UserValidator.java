package kz.zaletov.spring.springsecurity.util;

import kz.zaletov.spring.springsecurity.models.User;
import kz.zaletov.spring.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userService.findUserByUsername(user.getUsername())!=null)
            errors.rejectValue("username", "", "Такой пользователь уже существует");
        if(!user.getPasswordConfirm().equals(user.getPassword()))
            errors.rejectValue("password", "", "Пароли не совпадают");
    }
}
