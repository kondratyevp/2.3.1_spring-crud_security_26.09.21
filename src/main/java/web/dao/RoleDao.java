package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> allRoles(){
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    public Role getRoleById(long id){
        TypedQuery<Role> query = entityManager.createQuery
                ("select r from Role r where r.id=:id", Role.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    public Role getRoleByName (String role) {
        TypedQuery <Role> query = entityManager.createQuery
                ("select r from Role r where r.role=:role", Role.class);
        query.setParameter("role", role);
        return query.getSingleResult();
    }

    public Role getDefaultRole() {
        return getRoleByName("ROLE_USER");
    }
}
