package kz.zaletov.spring.springsecurity.controllers;

import jakarta.validation.Valid;
import kz.zaletov.spring.springsecurity.models.Person;
import kz.zaletov.spring.springsecurity.services.PersonDetailsService;
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

    private final PersonDetailsService personDetailsService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(PersonDetailsService personDetailsService, UserValidator userValidator) {
        this.personDetailsService = personDetailsService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("personForm", new Person());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("personForm") @Valid Person personForm, BindingResult bindingResult) {
        userValidator.validate(personForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        personDetailsService.register(personForm);
        return "redirect:/";
    }
}