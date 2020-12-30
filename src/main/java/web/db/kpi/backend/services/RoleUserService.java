package web.db.kpi.backend.services;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.db.kpi.backend.enums.Role;
import web.db.kpi.backend.interfaces.IRoleUserService;
import web.db.kpi.backend.models.RoleUser;
import web.db.kpi.backend.repositories.RoleUserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleUserService implements IRoleUserService {

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Override
    public List<RoleUser> getAll() {
        return roleUserRepository.findAll();
    }

    @Override
    public RoleUser getById(Long roleUserId) throws NotFoundException {
        Optional<RoleUser> roleUserMaybe = roleUserRepository.findById(roleUserId);
        if(roleUserMaybe.isPresent()){
            return roleUserMaybe.get();
        }
        else
            throw new NotFoundException("No user with such an Id: " + roleUserId);
    }

    @Override
    public RoleUser update(Long roleUserId, RoleUser roleUserDetails) throws NotFoundException {
        RoleUser oldRoleUser = getById(roleUserId);
        oldRoleUser.setUser(roleUserDetails.getUser());
        oldRoleUser.setRole(roleUserDetails.getRole());
        return roleUserRepository.save(oldRoleUser);
    }

    @Override
    public RoleUser create(RoleUser roleUserObject) {
        roleUserObject.setTimeCreated(new Date(System.currentTimeMillis()));
        return roleUserRepository.save(roleUserObject);
    }

    @Override
    public void delete(Long roleUserId) throws NotFoundException{
        roleUserRepository.delete(getById(roleUserId));
    }

    @Override
    public RoleUser getByLogin(String login) throws NotFoundException {
        Optional<RoleUser> userMaybe = roleUserRepository.findByLogin(login);
        if(userMaybe.isPresent()){
            return userMaybe.get();
        }
        else
            throw new NotFoundException("No user with such a login: " + login);
    }

    @Override
    public RoleUser getByLoginAndRole(String login, Role role) throws NotFoundException {
        Optional<RoleUser> userMaybe = roleUserRepository.findByLoginAndRole(login, role);
        if(userMaybe.isPresent()){
            return userMaybe.get();
        }
        else
            throw new NotFoundException("No user with such a login: " + login);
    }
}
