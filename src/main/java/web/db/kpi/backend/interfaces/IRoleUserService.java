package web.db.kpi.backend.interfaces;

import javassist.NotFoundException;
import web.db.kpi.backend.enums.Role;
import web.db.kpi.backend.models.RoleUser;

import java.util.List;

public interface IRoleUserService {

    List<RoleUser> getAll();
    RoleUser getById(Long roleUserId) throws NotFoundException;
    RoleUser create(RoleUser roleUserObject);
    RoleUser update(Long roleUserId, RoleUser roleUserDetails) throws NotFoundException;
    void delete(Long roleUserId) throws NotFoundException;
    RoleUser getByLogin(String login) throws NotFoundException;
    RoleUser getByLoginAndRole(String login, Role role) throws NotFoundException;
}
