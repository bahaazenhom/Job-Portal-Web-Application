package com.project.jobportal.controller;

import com.project.jobportal.entity.User;
import com.project.jobportal.service.UserService;
import com.project.jobportal.service.UserTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
    private final UserTypeService userTypeService;
    private final UserService userService;

    @Autowired
    public UserController(UserTypeService userTypeService, UserService userService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("getAllTypes", userTypeService.getAll());
        System.out.println(userTypeService.getAll());
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid User user, Model model){
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            model.addAttribute("error", "User already exists");
            model.addAttribute("getAllTypes", userTypeService.getAll());
            model.addAttribute("user", new User());
            return "register";
        }
        userService.addNew(user);
        return "redirect:/dashboard/";
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("Test");
       return "test";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model){

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        System.out.println("Logged out");
        return "redirect:/";
    }

    @GetMapping("/login-failed/")
    public String loginFailed(Model model){
        model.addAttribute("error","Wrong Username or Password");
        System.out.println("From User Controller Failure");
        return "login";
    }



}
