package kz.zaletov.spring.springsecurity.controllers;

import kz.zaletov.spring.springsecurity.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public AdminController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", personDetailsService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", personDetailsService.usergtList(userId));
        return "admin";
    }
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id){
        personDetailsService.delete(id);
        return "redirect:/admin";
    }
}
