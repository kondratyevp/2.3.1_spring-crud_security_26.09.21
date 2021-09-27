package web.service;

import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.model.Role;

import java.util.List;

@Service
public class RoleService {

    private final RoleDao roleDao;

    public RoleService (RoleDao roleDao){
        this.roleDao = roleDao;
    }

    public List<Role> allRoles(){
        return roleDao.allRoles();
    }

    public Role getRoleById(long id){
        return roleDao.getRoleById(id);
    }

    public Role getRoleByName (String role) {
        return roleDao.getRoleByName(role);
    }

}
