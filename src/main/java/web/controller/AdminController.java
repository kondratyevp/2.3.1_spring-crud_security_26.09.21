package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public AdminController() {}

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin/new")
    public String create (@ModelAttribute("newUser") User user,
                          @RequestParam(required=false) String roleAdmin){
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);

        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping ("/users")
    public String listUsers(Model model) {
        userService.allUsers();
        model.addAttribute("allusers", userService.allUsers());
//        model.addAttribute("idUser", userDao.getById(2));
        return "users";
    }

    @GetMapping ("/admin/{id}/edit")
    public String edit(Model model, @PathVariable ("id") int id){
        User user = userService.getById(id);
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            if (role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
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
        roles.add (roleService.getRoleByName("ROLE_USER"));
        if (roleAdmin!=null && roleAdmin.equals("ROLE_ADMIN")){
            roles.add (roleService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping ("/admin/users/{id}")
    public String delete (@PathVariable ("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }

}
