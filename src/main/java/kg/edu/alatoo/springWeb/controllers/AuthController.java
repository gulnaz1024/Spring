package kg.edu.alatoo.springWeb.controllers;

import jakarta.validation.Valid;
import kg.edu.alatoo.springWeb.modules.Book;
import kg.edu.alatoo.springWeb.modules.User;
import kg.edu.alatoo.springWeb.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/login")
    public String loginPage(@Valid User user) {
        if(user.getRoles().equals("ADMIN")){
            return "/";
        }
        return "/login";
    }
}