package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;
import web.service.UserDetailsServiceImp;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    public UserController() {}

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping ("/admin/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping ("/admin/new")
    public String create (@ModelAttribute("newUser") User user,
                          @RequestParam(required=false) String roleAdmin){
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getRoleByName("ROLE_USER"));
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleDao.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);

        userDao.add(user);
        return "redirect:/users";
    }

    @GetMapping ("/users")
    public String listUsers(Model model) {
        userDao.allUsers();
        model.addAttribute("allusers", userDao.allUsers());
//        model.addAttribute("idUser", userDao.getById(2));
        return "users";
    }

    @GetMapping("/{id}")
        public String showUser (@PathVariable("id") int id, Model model){
        model.addAttribute("user", userDao.getById(id));
        return "user";
    }


    @GetMapping ("/admin/{id}/edit")
    public String edit(Model model, @PathVariable ("id") int id){
        User user = userDao.getById(id);
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            if (role.equals(roleDao.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
        }
        model.addAttribute("user", user);
        return "edit";
    }

    @PatchMapping ("/admin/users/{id}")
    public String update (@ModelAttribute ("user") User user, @PathVariable ("id") int id,
                          @RequestParam(required=false) String roleAdmin){
        Set <Role> roles = new HashSet<>();
        roles.add (roleDao.getRoleByName("ROLE_USER"));
        if (roleAdmin!=null && roleAdmin.equals("ROLE_ADMIN")){
            roles.add (roleDao.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userDao.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping ("/admin/users/{id}")
    public String delete (@PathVariable ("id") int id) {
        userDao.delete(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/user/lk")
    public String getUserPage (Model model, Principal principal) {
        model.addAttribute("user", userDetailsServiceImp.loadUserByUsername(principal.getName()));
        return "user";
    }
}
