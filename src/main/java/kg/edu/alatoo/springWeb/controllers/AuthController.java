package kg.edu.alatoo.springWeb.controllers;

import jakarta.validation.Valid;
import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.modules.Borrower;
import kg.edu.alatoo.springWeb.modules.User;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import kg.edu.alatoo.springWeb.repos.UserRepository;

import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(@Valid User user) {
        if(user.getRoles().equals("ADMIN")){
            return "/";
        }
        return "/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        List<User> user = userRepository.findAll();
        return "/register";
    }

    @PostMapping("/register")
    public String addUser(@Valid User user, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            return "redirect:/register";
        }

        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "/logout";
    }
}