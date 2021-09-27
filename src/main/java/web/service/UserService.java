package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public List<User> allUsers(){
        return userDao.allUsers();
    }

    public void add(User user){
        userDao.add(user);
    }

    public void delete(long id){

        userDao.delete(id);
    }

    public void update (int id, User updatedUser){
        userDao.update(id, updatedUser);
    }

    public User getById(long id){
        return userDao.getById(id);
    }

    public User getUserByName (String name) {

        return userDao.getUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s);
    }
}
