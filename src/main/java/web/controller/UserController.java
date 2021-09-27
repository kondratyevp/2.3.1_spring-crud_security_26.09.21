package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

//    @Autowired
//    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

//    @Autowired
//    private UserService userDetailsServiceImp;

    public UserController() {}

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
        public String showUser (@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "user";
    }

    @GetMapping(value = "/user/lk")
    public String getUserPage (Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user";
    }
}
