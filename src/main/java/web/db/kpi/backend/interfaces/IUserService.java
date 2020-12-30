package web.db.kpi.backend.interfaces;

import javassist.NotFoundException;
import web.db.kpi.backend.models.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getById(Long userId) throws NotFoundException;
    User create(User userObject);
    User update(Long userId, User userDetails) throws NotFoundException;
    void delete(Long userId) throws NotFoundException;
    User getByLogin(String login) throws NotFoundException;
}
