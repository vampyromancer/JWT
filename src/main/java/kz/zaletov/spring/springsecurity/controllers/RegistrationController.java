package kz.zaletov.spring.springsecurity.controllers;

import jakarta.validation.Valid;
import kz.zaletov.spring.springsecurity.models.User;
import kz.zaletov.spring.springsecurity.services.UserService;
import kz.zaletov.spring.springsecurity.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.register(userForm);
        return "redirect:/";
    }
}