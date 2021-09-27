package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.dao.UserDao;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;
//    @Autowired
//    private RoleDao roleDao;

    @Autowired
    private UserService userDetailsServiceImp;

    public UserController() {}

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
        public String showUser (@PathVariable("id") int id, Model model){
        model.addAttribute("user", userDao.getById(id));
        return "user";
    }

    @GetMapping(value = "/user/lk")
    public String getUserPage (Model model, Principal principal) {
        model.addAttribute("user", userDetailsServiceImp.loadUserByUsername(principal.getName()));
        return "user";
    }
}
